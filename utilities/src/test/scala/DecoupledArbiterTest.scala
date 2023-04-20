import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class DecoupledArbiterTest extends AnyFlatSpec with ChiselScalatestTester {
  "Test no valid input" should "pass" in {
    test(new DecoupledArbiter(3)){ dut =>
      //set no valid
      for(i <- 0 until 3){
        dut.io.in(i).valid.poke(false.B)
        dut.io.in(i).bits.poke(i.U)
        dut.io.out.ready.poke(true.B)
      }
      dut.io.out.valid.expect(false.B)
    }
  }

  "Test single valid input with backpressure" should "pass" in {
    test(new DecoupledArbiter(3)) { dut =>
      //set no valid
      for (i <- 0 until 3) {
        dut.io.in(i).valid.poke(true.B)
        dut.io.in(i).bits.poke(i.U)
        dut.io.out.ready.poke(true.B)

        dut.io.out.ready.poke(false.B) // not ready to get data from the result side
        dut.io.in(i).ready.expect(false.B) //coressponding busy status

        dut.io.out.ready.poke(true.B)
        dut.io.in(i).valid.poke(false.B)
      }
    }
  }

  "Test multiple input ready with backpressure" should "pass" in {
    test(new DecoupledArbiter(3)){ dut =>
      dut.io.in(1).valid.poke(true.B)
      dut.io.in(1).bits.poke(1.U)
      dut.io.in(2).valid.poke(true.B)
      dut.io.in(2).bits.poke(2.U)
      dut.io.out.ready.poke(true.B)

      dut.io.out.bits.expect(1.U)
      dut.io.in(1).ready.expect(true.B)
      dut.io.in(0).ready.expect(false.B)

      dut.io.out.ready.poke(false.B)
      dut.io.in(1).ready.expect(false.B)
    }
  }

}