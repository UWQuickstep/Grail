/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

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
