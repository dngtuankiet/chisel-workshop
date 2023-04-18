import chisel3._
import chisel3.util._

//Check Verilog file to see the reset

class ShiftRegister(val init: Int = 1) extends Module{
  val io = IO( new Bundle{
    val in = Input(Bool())
    val out = Output(UInt(4.W))
  })

  val state = RegInit(UInt(4.W),init.U) //initialize from variable

  val nextState = (state << 1) | io.in
  state := nextState
  io.out := state

}

class ParamShiftRegister(val n: Int, val init: BigInt = 1) extends Module{
  val io = IO(new Bundle{
    val in = Input(Bool())
    val i_en = Input(Bool())
    val out = Output(UInt(n.W)) //parameterized shift stage
  })

  val state = RegInit(UInt(n.W), init.U)

  val nextState = (state << 1) | io.in
  when(io.i_en){
    state := nextState
  }
  io.out := state
}

object ShiftRegisterMain extends App{
  println("Generate ShiftRegister Verilog file!")
  emitVerilog(new ShiftRegister(2), Array("--target-dir","generated"))
  emitVerilog(new ParamShiftRegister(4,2), Array("--target-dir","generated"))
}