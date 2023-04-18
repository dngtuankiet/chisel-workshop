import chisel3._
import chisel3.util._

class ExplicitClockReset extends Module {
  val io = IO(new Bundle {
    val i_clk = Input(Clock())
    val i_rst = Input(Bool()) //active HIGH
    val in = Input(UInt(1.W))
    val outImplicit = Output(UInt(1.W))
    val outExplicitReset = Output(UInt(1.W))
    val outExplicitClock = Output(UInt(1.W))
    val outExplicitBoth = Output(UInt(1.W))
  })

  val reg = RegInit(0.U(1.W))
  reg := io.in
  io.outImplicit := reg

  withClock(io.i_clk){
    //explicit clock
    val regExplicitClock = RegInit(0.U(1.W))
    regExplicitClock := io.in
    io.outExplicitClock := regExplicitClock
  }

  withReset(io.i_rst){
    //explicit reset
    val regExplicitReset = RegInit(0.U(1.W))
    regExplicitReset := io.in
    io.outExplicitReset := regExplicitReset
  }

  withClockAndReset(io.i_clk,io.i_rst){
    //explicit clock and reset
    val regExplicitBoth = RegInit(0.U(1.W))
    regExplicitBoth := io.in
    io.outExplicitBoth := regExplicitBoth
  }
}

object ExplicitClockResetMain extends App {
  println("Generate ExplicitClockResetMain Verilog file")
  emitVerilog(new ExplicitClockReset(), Array("--target-dir","generated/"))
}