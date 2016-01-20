/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;
/**
 * @brief This block is for combining messages.
 *
 */
public class CombineMsgBlock extends Block {

  private String aggFunc;

  /**
   * @brief Constructor.
   * @param stage The string indicate the stage of this code block.
   * @param indent The indent level.
   * @param aggFunc The aggregation function.
   */
  public CombineMsgBlock(String stage, int indent, String aggFunc) {
    super(stage, indent);
    this.aggFunc = aggFunc;
    this.append("SELECT message.id as id, " + aggFunc + " as val");
    this.append("INTO cur");
    this.append("FROM message" + ";");
    this.sql = this.sb.toString();
  }

  /**
   * @brief Return the aggregation function.
   * @return The aggregation function.
   */
  public String getAggFunc() {
    return this.aggFunc;
  }
}
