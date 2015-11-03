/*
Copyright (c) 2015,
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the University of Wisconsin-Madison nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL JING FAN, ADALBERT GERALD SOOSAI RAJ, AND JIGNESH
M. PATEL BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

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
