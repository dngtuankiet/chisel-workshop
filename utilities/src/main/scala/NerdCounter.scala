import chisel3._
import chisel3.util._
import scala.math._

//Comparing maximum value of counter in FPGA and ASIC are different
//Count down in ASIC might save some logic
//In FPGA, anything will do

case class NerdCounterParameter(n: Int, width: Int){
    val pow = Math.pow(width.asInstanceOf[Double],2).asInstanceOf[Int]-1
    require(n <= pow)
}

class NerdCounter(val param: NerdCounterParameter) extends Module{
    val io = IO(new Bundle{
    val o_tick = Output(Bool())
})

    val MAX = (param.n - 2).S(param.width.W)
    val cntReg = RegInit(MAX)
    io.o_tick := false.B

    cntReg := cntReg - 1.S
    when(cntReg(7)){
        io.o_tick := true.B
        cntReg := MAX
    }
}

object NerdCounterMain extends App {
    println("Generating NerdCounter Verilog file")

    val param = new NerdCounterParameter(20,8)
    emitVerilog(new NerdCounter(param), Array("--target-dir", "generated/"))
}