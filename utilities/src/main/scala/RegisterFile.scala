import chisel3._

/**
 * RISCV 32-bit Register File for RV32I with a single write port and a parameterized number of read ports
 * Writes will only be performed when "wen" is asserted
 */
class RegisterFile(readPorts: Int) extends Module {
  require(readPorts >= 0)
  val io = IO(new Bundle{
    val i_wen = Input(Bool())
    val i_waddr = Input(UInt(5.W))
    val i_wdata = Input(UInt(32.W))
    val i_raddr = Input(Vec(readPorts, UInt(5.W)))
    val o_rdata = Output(Vec(readPorts, UInt(32.W)))
  })

  //register initialization of UInts
  val reg = RegInit(VecInit(Seq.fill(32)(0.U(32.W))))

  when(io.i_wen){
    reg(io.i_waddr) := io.i_wdata
  }

  for(i <- 0 until readPorts){
    when(io.i_raddr(i) === 0.U){ // register 0 is always 0
      io.o_rdata(i) := 0.U
    }.otherwise{
      io.o_rdata(i) := reg(io.i_raddr(i))
    }
  }
}

object RegisterFileMain extends App{
  val readPorts: Int = 2
  println(s"Generate simple RV32I register file with 1 write port and $readPorts read port(s)")
  emitVerilog(new RegisterFile(readPorts), Array("--target-dir", "generated"))
}
