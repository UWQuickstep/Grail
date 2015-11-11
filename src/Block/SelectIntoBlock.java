/*
Copyright (c) 2015,
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the University of Wisconsin-Madison nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL JING FAN, ADALBERT GERALD SOOSAI RAJ, AND JIGNESH
M. PATEL BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package Block;

import java.util.ArrayList;

/**
 * @brief The SQL block for select into statement. Typically, the form is：
 * SELECT a AS A, b AS B
 * FROM C, D, E
 * WHERE predicates
 */
public class SelectIntoBlock extends Block {

  private ArrayList<String> lines = new ArrayList<String>();
  /**
   * @brief Constructor.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param attrs The attribute list.
   * @param targetTb The name of the new table.
   * @param fromList The names of the from table.
   * @param pred The predicates.
   * @param joinWithId Whether to join these tables with common vertex id.
   * @param groupBy The attribute that we should add group by operation on.
   */
  public SelectIntoBlock(String stage,
                         int indent,
                         ArrayList<String> attrs,
                         String targetTb,
                         ArrayList<String> fromList,
                         String pred,
                         boolean joinWithId,
                         String groupBy) {
    super(stage, indent);
    StringBuilder line = new StringBuilder();
    line.append("SELECT ");
    for (int i = 0; i < attrs.size() - 1; ++i) {
      line.append(attrs.get(i) + ", ");
    }
    line.append(attrs.get(attrs.size() - 1));
    this.append(line.toString());
    if (targetTb != null) {
      // If target table is null, means this is just a selection instead
      // of insertion.
      this.append("INTO " + targetTb);
    }
    line.delete(0, line.length());
    for (int i = 0; i < fromList.size() - 1; ++i) {
      line.append(fromList.get(i) + ", ");
    }
    line.append(fromList.get(fromList.size() - 1));
    this.append("FROM " + line.toString());
    line.delete(0, line.length());
    if (joinWithId) {
      line.append("WHERE ");
      for (int i = 0; i < fromList.size() - 2; ++i) {
          if (fromList.get(i).equals("edge")
             || fromList.get(i+1).equals("edge")) {
              continue;
          }
          if ( 0 == i) {
            line.append(fromList.get(i) + ".id = " + fromList.get(i+1)
                + ".id ");
          } else {
            line.append("AND " + fromList.get(i) + ".id = " + fromList.get(i+1)
                + ".id ");
          }
      }
      if (fromList.size() >= 2 &&
         !fromList.get(fromList.size() - 2).equals("edge")
         && !fromList.get(fromList.size() - 1).equals("edge")) {
        if (fromList.size() == 2) {
          line.append(fromList.get(fromList.size() - 2)
              + ".id = " + fromList.get(fromList.size() - 1) + ".id");
        } else {
          line.append(" AND " + fromList.get(fromList.size() - 2)
              + ".id = " + fromList.get(fromList.size() - 1) + ".id");
        }
      }

      if (pred != null) {
          if (line.length() == 6) {
              line.append(pred);
          }
          else {
              line.append(" AND " + pred);
          }
      }
    } else {
      if (pred != null) {
        line.append("WHERE " + pred);
      }
    }
    if (line.length() != 0) {
      this.append(line.toString());
    }

    if (groupBy != null) {
      this.append("GROUP BY " + groupBy);
    }

    this.append(";");

    this.sql = this.sb.toString();
  }

  /**
   * @brief Constructor. The constructor take into consideration the
   * situation when we need union/intersect two tables.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param attrs The attribute list.
   * @param targetTb The name of the new table.
   * @param lhs The SQL to generate the left table.
   * @param rhs The SQL to generate the right table.
   * @param op The operation on these two tables.
   */
  public SelectIntoBlock(String stage,
               int indent,
               ArrayList<String> attrs,
               String targetTb,
               SelectIntoBlock lhs,
               SelectIntoBlock rhs,
               String op) {
    super(stage, indent);
    StringBuilder line = new StringBuilder();
    line.append("SELECT ");
    for (int i = 0; i < attrs.size() - 1; ++i) {
      line.append(attrs.get(i) + ", ");
    }
    line.append(attrs.get(attrs.size() - 1));
    this.append(line.toString());
    this.append("INTO " + targetTb);
    this.append("FROM (");
    this.concat(lhs.getSql());
    this.append(op, indent + 1);
    this.concat(rhs.getSql());
    this.append(")s");
    this.sql = this.sb.toString();
  }
}
