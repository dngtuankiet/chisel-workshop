import chisel3._
import chisel3.util._

class DemoAddModule extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(32.W))
    val out = Output(UInt(32.W))
  })
  val r_in = RegInit(0.U(32.W))
  r_in := io.in

  io.out := r_in + 1.U
}

/**************************************************/
/**
 * Should apply Decoupled to wrap user-defined data (for example Bundle)
 */
/**************************************************/
class AddModuleInput extends Bundle{
  val value = UInt(32.W)
}

class AddModuleOutput extends Bundle{
  val result = UInt(32.W)
  val resultAdd2 = UInt(32.W)
}

class DecoupledAddModule extends Module {
  val io = IO(new Bundle {
    val in = Flipped(Decoupled(new AddModuleInput))
    val out = Decoupled(new AddModuleOutput)
  })
  //Initialize all elements. Dont want firrtl complaining about "not fully initialized" connections
  val r_value = RegInit(0.U(32.W))
  val r_result = RegInit(0.U(32.W))
  val r_resultAdd2 = RegInit(0.U(32.W))
//  val r_resultValid = RegInit(0.U(32.W))
  val r_busy = RegInit(false.B)

  //Store input data
  when(io.in.valid && !r_busy){
    r_value := io.in.bits.value //store new input
    r_busy := true.B //new input => obviously busy
//    r_resultValid := false.B //output is no longer valid if there is new input
  }
  io.in.ready := !r_busy

  //The core is busy, do some operation here
  when(r_busy){
    r_result := r_value + 1.U
    r_resultAdd2 := r_value + 2.U
//    r_resultValid := true.B //takes 1 cycle to operate
    r_busy := false.B
  }

  //Assign the result and Assert result valid (take 1 clock to execute)
  io.out.bits.result := r_result
  io.out.bits.resultAdd2 := r_resultAdd2
  io.out.valid := !r_busy
//  when(io.out.valid) {
//    r_busy := false.B
//  }
}

object DemoAddModuleMain extends App{
  println("Test Decoupled interface with DemoAddModule")
  emitVerilog(new DemoAddModule, Array("--target-dir", "generated/Decoupled"))
  emitVerilog(new DecoupledAddModule, Array("--target-dir", "generated/Decoupled"))
}