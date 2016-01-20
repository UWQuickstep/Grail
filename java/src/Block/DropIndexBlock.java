/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for dropping an index.
 */
public class DropIndexBlock extends Block {

  /**
   * @brief Constructor.
   * @param stage The string indicate the stage of this code block.
   * @param indent The indent level.
   * @param index The name of the index to be dropped.
   * @param tbName The name of the table containing the index.
   */
  public DropIndexBlock(String stage, int indent, String index, String tbName) {
    super(stage, indent);
    this.append("DROP INDEX " + index + " ON " + tbName + ";");
    this.sql = this.sb.toString();
  }

}
