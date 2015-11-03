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
    this.append("WHERE next.id = " + otherTable + ".id");
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
