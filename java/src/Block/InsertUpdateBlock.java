/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for using INSERT to express UPDATE.
 */
public class InsertUpdateBlock extends Block {
  // The old table name.
  private String otherTable;

  /**
   * @brief Constructor.
   * @param block The original UpdateVertexBlock (the InsertUpdateBlock is
   * just another way to write the UpdateVertexBlock).
   */
  public InsertUpdateBlock(UpdateVertexBlock block) {
    super(block.getStage(), block.getIndentLevel());
    this.otherTable = block.getOtherTable();

    this.append("INSERT INTO " + otherTable);
    this.append("SELECT *");
    this.append("FROM next");
    this.append("WHERE NOT EXISTS (");
    this.append("  SELECT * FROM " + otherTable);
    this.append("  WHERE " + otherTable + ".id = next.id)");
    this.append("DROP TABLE next;");
    this.append("EXEC SP_RENAME '"+ otherTable +"','next'" + ";");
    this.sql = this.sb.toString();
  }

}
