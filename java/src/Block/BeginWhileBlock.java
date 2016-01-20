/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief Begin while block.
 *
 */
public class BeginWhileBlock extends Block {

  /**
   * @brief Constructor.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param endStr The string indicates the termination condition of the
   *               iterations.
   */
  public BeginWhileBlock(String stage, int indent, String endStr) {
    super(stage, indent);
    String initVal = "-1";
    if (!endStr.equals("NO_MESSAGE")) {
      initVal = endStr.substring(endStr.indexOf('(') + 1,
                                 endStr.indexOf(')'))
                      .split(",")[1].trim();
    }
    this.append("DECLARE @flag int");
    this.append("SET @flag = " + initVal);
    this.append("WHILE @flag != 0");
    this.append("BEGIN");
    this.sql = this.sb.toString();
  }

}
