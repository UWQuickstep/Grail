/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for updating the vertex value.
 *
 */
public class UpdateVertexBlock extends Block{
  // The name of the other table used in update.
  private String otherTable;
  private String valueExpression;

  /**
   * @brief Constructor.
   * @param stage
   * @param indent
   * @param otherTable The name of the other table.
   * @param oVal The attribute name or scalar function of new value.
   */
  public UpdateVertexBlock(String stage,
                           int indent,
                           String otherTable,
                           String valueExpression) {
    super(stage, indent);
    this.otherTable = otherTable;
    this.valueExpression = valueExpression;
    this.append("UPDATE next SET next.val = " + valueExpression);
    this.append("FROM next, " + otherTable);
    this.append("WHERE next.id = " + otherTable + ".id" + ";");
    
    this.sql = this.sb.toString();
  }

  /**
   * @brief Get the other table name.
   * @return The other table name.
   */
  public String getOtherTable() {
    return this.otherTable;
  }

  /**
   * @brief Get the value expression.
   * @return The value expression.
   */
  public String getValueExpression() {
      return this.valueExpression;
  }
}
