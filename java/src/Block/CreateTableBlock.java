/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The block of SQL to create a table.
 *
 */
public class CreateTableBlock extends Block {

  /**
   * @brief Constructor.
   * @param stage What's this block for.
   * @param indent Indentation level.
   * @param tableName The new table name.
   * @param attrs The attributes, every attr should contain full description
   * including attribute name, type.
   */
  public CreateTableBlock(String stage,
                          int indent,
                          String tableName,
                          String[] attrs) {
    super(stage, indent);
    this.append("CREATE TABLE " + tableName + "(");
    for (String attr : attrs) {
      this.append(attr + ",", indent + 1);
    }
    this.append(")" + ";");
    this.sql = this.sb.toString();
  }

}
