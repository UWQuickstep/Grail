/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief Basic SQL statement block.
 */
public class Block {
  // Description of the target of this block. Such as "sendMsg", "combineMsg".
  protected String stage;
  // Buffer for intermediate SQL.
  protected StringBuilder sb;
  // SQL statement.
  protected String sql;
  // The initial level of indentation of this block.
  protected int indentLevel;

  /**
   * @brief Constructor.
   * @param stage The stage.
   * @param indentLevel The level of indentation.
   * @param sql The sql statement.
   */
  public Block(String stage, int indentLevel, String sql) {
    this.stage = stage;
    this.indentLevel = indentLevel;
    this.sb = new StringBuilder();
    this.append(sql);
    this.sql = this.sb.toString();
  }

  /**
   * @brief Constructor.
   * @param stage The stage.
   * @param sql The sql statement.
   */
  public Block(String stage, String sql) {
    this.stage = stage;
    this.sql = sql;
  }

  /**
   * @brief Constructor.
   * @param stage The stage.
   * @param indentLevel The level of indentation.
   */
  public Block(String stage, int indentLevel) {
    this.stage = stage;
    this.indentLevel = indentLevel;
    this.sb = new StringBuilder();
  }

  /**
   * @brief Getters and Setters.
   */
  public String getStage() {
    return this.stage;
  }

  public String getSql() {
    this.sql = this.sb.toString();
    return this.sql;
  }

  public int getIndentLevel() {
    return indentLevel;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public void setIndentLevel(int indentLevel) {
    this.indentLevel = indentLevel;
  }

  /**
   * @brief Print the information of the block.
   */
  public void print() {
    if (sql == null) {
      sql = this.sb.toString();
    }
    System.out.println(sql);
  }

  /**
   * @brief Append SQL with indentation.
   * @param stat The SQL statement.
   * @param indent Indentation level.
   */
  public void append(String stat, int indent) {
    StringBuilder line = new StringBuilder();
    for (int i = 0; i < indent; ++i) {
      line.append("  ");
    }
    line.append(stat);
    this.sb.append(line);
    this.sb.append("\n");
  }

  public void concat(String stat) {
    this.sb.append(stat);
  }

  public void append(String stat) {
    this.append(stat, this.indentLevel);
  }

  public String toString() {
    return this.sb.toString();
  }
}
