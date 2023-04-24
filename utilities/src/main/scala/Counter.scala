import chisel3._
import chisel3.util._

class MyCounter(depth: Int = 8) extends Module{
  val io = IO(new Bundle{
    val en = Input(Bool())
    val out = Output(UInt(depth.W))
    val overflow = Output(Bool())
  })

  //Check source for return types
  //source: https://github.com/chipsalliance/chisel/blob/main/src/main/scala/chisel3/util/Counter.scala

//  val write_counter = Counter(io.en, depth*2)._1 //"._1" for access an element in a tuple
//  io.out := write_counter

  val tuple_return = Counter(io.en, depth*2)
  io.out := tuple_return._1
  io.overflow := tuple_return._2
}

object MyCounterMain extends App {
  println("Generating MyCounter Verilog file")
  emitVerilog(new MyCounter(8), Array("--target-dir", "generated/"))
}
