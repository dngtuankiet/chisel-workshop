import chisel3._
import chisel3.util._

class BlackBoxAdderIO extends Bundle{
    val i_a = Input(UInt(32.W))
    val i_b = Input(UInt(32.W))
    val i_carry = Input(Bool())
    val o_c = Output(UInt(32.W))
    val o_carry = Output(Bool())
}

class InlineBlackBoxAdder extends HasBlackBoxInline {
    val io = IO(new BlackBoxAdderIO())
    setInline("InlineBlackBoxAdder.v",
    s"""
    |module InlineBlackBoxAdder(
    |input [31:0] i_a,
    |input [31:0] i_b,
    |input i_carry,
    |output [31:0] o_c,
    |output o_carry
    |);
    |
    |wire [32:0] sum;
    |
    |assign sum = i_a + i_b + i_carry;
    |assign o_c = sum[31:0];
    |assign o_carry = sum[32];
    |
    |endmodule
    |""".stripMargin)
}


/**
  * Two alternative to inlined blackbox:
  */


// Expect to find Verilog file in the ./src/main/resource folder
class ResourceBlackBoxAdder extends HasBlackBoxResource{
    val io = IO(new BlackBoxAdderIO)
    addResource("/ResourceBlackBoxAdder.v")
}

// Provide relative path from the project folder
class PathBlackBoxAdder extends HasBlackBoxPath{
    val io = IO(new BlackBoxAdderIO)
    addPath("./src/main/resources/PathBlackBoxAdder.v")
}



/**
  * Call out a BlackBox to use
  */

class InlineAdder extends Module {
    val io = IO(new BlackBoxAdderIO)
    val adder = Module(new InlineBlackBoxAdder)

    io <> adder.io //bulk connect - same io name and width
}

/**
  * HasBlackBoxInline, HasBlackBoxPath, HasBlackBoxResource are traits that extend Chisel's BlackBox class
  * meaning that, e.g., 
  * class Example extends BlackBox with HasBlackBoxInline  === class Example extends HasBlackBoxInline
  */

object InlineAdderMain extends App{
    println("Generating BlackBoxAdder Verilog file!")
    emitVerilog(new InlineAdder(), Array("--target-dir", "generated"))
}

