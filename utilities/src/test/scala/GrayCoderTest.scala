import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.math.pow

class GrayCoderTest extends AnyFlatSpec with ChiselScalatestTester {
  "Test gray coder" should "pass" in {
    test(new GrayCoder(4)){ dut =>
      def toBinary(i: Int, digits: Int = 8) = {
        String.format("%" + digits + "s", i.toBinaryString).replace(' ', '0')
      }

      println("Encoding:")
      for (i <- 0 until pow(2, 4).toInt) {
        dut.io.in.poke(i.U)
        dut.io.encode.poke(true.B)
        dut.clock.step(1)
        println(s"In = ${toBinary(i, 4)}, Out = ${toBinary(dut.io.out.peek().litValue.toInt, 4)}")
      }

      println("Decoding:")
      for (i <- 0 until pow(2, 4).toInt) {
        dut.io.in.poke(i.U)
        dut.io.encode.poke(false.B)
        dut.clock.step(1)
        println(s"In = ${toBinary(i, 4)}, Out = ${toBinary(dut.io.out.peek().litValue.toInt, 4)}")
      }

    }
  }
}