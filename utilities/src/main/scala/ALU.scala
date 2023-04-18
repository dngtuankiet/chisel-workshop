import chisel3._
import chisel3.util._

class ALU extends Module {
    val io = IO(new Bundle{
        val i_a = Input(UInt(8.W))
        val i_b = Input(UInt(8.W))
        val i_fn = Input(UInt(2.W))
        val o_y = Output(UInt(8.W))
    })

    //some default value is needed
    io.o_y := 0.U

    //ALU operation
    switch(io.i_fn){
        is(0.U) {io.o_y := io.i_a + io.i_b}
        is(1.U) {io.o_y := io.i_a - io.i_b}
        is(2.U) {io.o_y := io.i_a | io.i_b}
        is(3.U) {io.o_y := io.i_a & io.i_b}
    }
}

object ALU extends App{ //there is a "final main" inside App
  //main method
  println("Generating ALU Verilog file!")
  emitVerilog(new ALU(), Array("--target-dir","generated/"))
}