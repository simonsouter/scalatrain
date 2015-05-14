/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

object implicits {
  implicit class ListOps(list: List[Path]) {
    def random = {
      List(4, 5, 6)
    }
    def sortPathCost = {
      list.sorted(pathCostOrdering)
    }

    def sortPathTime = {
      list.sorted(pathTimeOrdering)
    }
  }
}
