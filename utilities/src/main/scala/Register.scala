import chisel3._
import chisel3.util._
// import chisel3.tester._
// import chisel3.tester.RawTester.test

class Register extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(1.W))
    val out = Output(UInt(1.W))
  })

  val reg = Reg(UInt(1.W))
  reg := io.in
  io.out := reg
}

class RegisterInit extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(1.W))
    val out = Output(UInt(1.W))
  })

  val reg = RegInit(0.U(1.W))
  reg := io.in
  io.out := reg
}

class RegisterNext extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(1.W))
    val out = Output(UInt(1.W))
  })

  //register bit width is inferred from io.out
  io.out := RegNext(io.in + 1.U)
}

class CombFilter extends Module{
  val io = IO(new Bundle{
    val in = Input(SInt(8.W))
    val out = Output(SInt(8.W))
  })

  val delay: SInt = Reg(SInt(8.W)) //cast delay to be an SInt - can do any operation with it
  delay := io.in
  io.out := io.in - delay
}
/**
 * An object extending App to generate the Verilog code.
 */
object RegisterMain extends App{ //there is a "final main" inside App
  //main method
  println("This is the object generating Verilog file!")
  emitVerilog(new Register(), Array("--target-dir","generated/"))
  emitVerilog(new RegisterInit(), Array("--target-dir","generated/"))
  emitVerilog(new RegisterNext(), Array("--target-dir","generated/"))
  emitVerilog(new CombFilter(), Array("--target-dir","generated/"))
}


/**
  * Another way to create main
  */
// object RegisterMain{
//   //main method
//   def main(args: Array[String])
//   {
//     println("This is the object generating Verilog file!")
//     emitVerilog(new Register(), Array("--target-dir","generated/"))

//   }
// }

