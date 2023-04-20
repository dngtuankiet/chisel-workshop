import chisel3._
import chisel3.util._

/**
 * Decoupled arbiter: a module that has n Decoupled inputs and one Decoupled output.
 * The arbiter selects the lowest channel that is valid and forward it to the output.
 * @param numChannels
 */
class DecoupledArbiter(numChannels: Int) extends Module{
  val io = IO(new Bundle{
    val in = Vec(numChannels, Flipped(Decoupled(UInt(8.W))))
    val out = Decoupled(UInt(8.W))
  })

  //io.out.valid is true if any of the inputs are valid
  io.out.valid := io.in.map(_.valid).reduce(_||_)

  val w_selected =  PriorityMux(io.in.map(_.valid).zipWithIndex.map{
    case(valid, index) => (valid, index.U)
  })

  io.out.bits := io.in(w_selected).bits

  //each input's ready is true if the output is ready, AND that channel is selected
  io.in.map(_.ready).zipWithIndex.foreach{case (ready, index) =>
    ready := io.out.ready && w_selected === index.U
  }

}