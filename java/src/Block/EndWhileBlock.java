/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for end while.
 */
public class EndWhileBlock extends Block {
  private String endStr;

  /**
   * @brief Constructor.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param endStr The string indicates the termination condition of the
   *               iterations.
   * @param counter The name of the table that is used to control the
   *                 iterations.
   */
  public EndWhileBlock(String stage,
                       int indent,
                       String endStr,
                       String counter) {
    super(stage, indent);
    this.endStr  = endStr;
    if (endStr.equals("NO_MESSAGE")) {
      this.append("SELECT @flag = COUNT (*) FROM " + counter + ";");
    } else {
      this.append("SET @flag = @flag - 1");
    }
    this.append("END", indent - 1);
    this.sql = this.sb.toString();
  }

  /**
   * @brief Get the termination condition.
   * @return The termination condition.
   */
  public String getEndStr() {
    return endStr;
  }
}
