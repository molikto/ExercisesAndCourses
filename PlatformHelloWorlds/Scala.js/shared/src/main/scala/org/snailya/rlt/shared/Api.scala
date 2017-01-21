package org.snailya.rlt.shared

/**
  * Created by molikto on 8/6/16.
  */
trait Api {

  def list(path: String): Seq[String]
}
