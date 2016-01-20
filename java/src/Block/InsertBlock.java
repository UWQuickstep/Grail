/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for  INSERT INTO SELECT FROM clause.
 */
public class InsertBlock extends Block {
  /**
   * @brief Constructor.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param attr The attribute list from the table.
   * @param targetTb The name of the new table.
   * @param fromTb The name of the from table.
   */
  public InsertBlock(String stage,
                     int indent,
                     String attr,
                     String targetTb,
                     String fromTb) {
    super(stage, indent);
    this.append("INSERT INTO " + targetTb);
    this.append("SELECT " + attr);
    this.append("FROM " + fromTb + ";");
    this.sql = this.sb.toString();
  }

}
