import chisel3._
import chisel3.util._
//import chisel3.tester._                 //BootCamp specificly
//import chisel3.tester.RawTester.test    //BootCamp specificly
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class DemoAddModuleTest extends AnyFlatSpec with ChiselScalatestTester {
  "DemoAddModule" should "pass" in {
    test(new DemoAddModule()){ dut =>
      def gen_1_test(test: Int)={
        dut.io.in.poke(test)
        dut.clock.step(1)
        dut.io.out.expect(test+1)
      }
      gen_1_test(2)
    }
  }
}

class DecoupledAddModuleTest extends AnyFlatSpec with ChiselScalatestTester {
  "DemoAddModule" should "pass" in {
    test(new DecoupledAddModule()).withAnnotations(Seq(WriteVcdAnnotation)){ dut =>
      def gen_1_test(test: Int)={
        dut.io.out.ready.poke(true.B)
        dut.io.in.bits.value.poke(test)
        dut.io.in.valid.poke(true.B)
        println("Starting")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        if(dut.io.in.ready.peek().litValue == 0){
          dut.io.in.valid.poke(false.B)
          println("Input stored")
        }
        println("Step 1")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        println("Step 2")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        println("Step 3")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        println("Step 4")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        dut.io.in.bits.value.poke(test+3)
        dut.io.in.valid.poke(true.B)
        println("Step 5")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        if (dut.io.in.ready.peek().litValue == 0) {
          dut.io.in.valid.poke(false.B)
          println("Input stored")
        }
        println("Step 6")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")

        dut.clock.step(1)
        println("Step 7")
        println(s"--io.in: ready=${dut.io.in.ready.peek().litValue}")
        println(s"--result=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.result.peek().litValue}")
        println(s"--resultAddd2=${dut.io.out.valid.peek().litValue}, bits=${dut.io.out.bits.resultAdd2.peek().litValue}")
//        dut.io.out.bits.result.expect(test+1)
//        dut.io.out.bits.resultAdd2.expect(test+2)
      }
      gen_1_test(2)
    }
  }
}