/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for flow control.
 */
public class FlowControlBlock extends Block {
    /**
     * @brief Constructor.
     * @param stage The stage string.
     * @param indent The indent level.
     * @param flowControl The flow control condition.
     * @param lhs The SQL block in if clause.
     * @param rhs The SQL block in else clause.
     */
  public FlowControlBlock(String stage,
                          int indent,
                          String flowControl,
                          String lhs,
                          String rhs) {
    super(stage, indent);
    this.append("IF (" + flowControl + ")");
    this.append("BEGIN");
    this.concat(lhs);
    this.append("END");
    this.append("ELSE");
    this.append("BEGIN");
    this.concat(rhs);
    this.append("END");
    this.sql = this.sb.toString();
  }

}
