import chisel3._
import chisel3.util._
import scala.math.pow

class GrayCoder(bitwidth: Int) extends  Module{
  val io = IO(new Bundle{
    val in = Input(UInt(bitwidth.W))
    val out = Output(UInt(bitwidth.W))
    val encode  = Input(Bool())
  })
  when(io.encode) { //encode
   io.out := io.in ^ (io.in >> 1.U)
  } .otherwise{
    io.out := Seq.fill(log2Ceil(bitwidth))(Wire(UInt(bitwidth.W))).zipWithIndex.fold((io.in,0)){
      case ((w1: UInt, i1: Int), (w2: UInt, i2: Int )) => {
        w2 := w1 ^ (w1 >> pow(2, log2Ceil(bitwidth)-i2-1).toInt.U)
        (w2, i1)
      }
    }._1
  }
}


class AsyncFIFO(depth: Int = 16) extends Module {
  val io = IO(new Bundle{
    //write inputs
    val i_wr_clk = Input(Clock())
    val i_wr_en = Input(Bool())
    val i_wr_data = Input(UInt(32.W))
    //read inputs/outputs
    val i_rd_clk = Input(Clock())
    val i_rd_en = Input(Bool())
    val o_rd_data = Output(UInt(32.W))

    //FIFO status
    val o_full = Output(Bool())
    val o_empty = Output(Bool())
  })

  //check valid depth input
  assert(isPow2(depth), "AsyncFIFO needs a power-of-two depth")
  val write_counter = withClock(io.i_wr_clk){
    Counter(io.i_wr_en && !io.o_full, depth*2)._1
  }
  val read_counter = withClock(io.i_rd_clk){
    Counter(io.i_rd_en && !io.o_empty, depth*2)._1
  }

  //encode
  val encoder = new GrayCoder(write_counter.getWidth)
  encoder.io.in := write_counter
  encoder.io.encode := true.B

  //double flop for synchronization
  val sync = withClock(io.i_rd_clk){ShiftRegister(encoder.io.out, 2)}

  //decode
  val decoder = new GrayCoder(read_counter.getWidth)
  decoder.io.in := sync
  decoder.io.encode := false.B

  //status
  io.o_full := Mux(write_counter === (depth*2).asUInt, true.B, false.B)
  io.o_empty := Mux(read_counter === 0.U, true.B, false.B)

}
