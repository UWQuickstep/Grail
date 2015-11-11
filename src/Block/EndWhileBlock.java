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
 * @brief The SQL block for end while.
 */
public class EndWhileBlock extends Block {
  private String endStr;

  /**
   * @brief Constructor.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param endStr The string indicates the termination condition of the
   *               iterations.
   * @param counter The name of the table that is used to control the
   *                 iterations.
   */
  public EndWhileBlock(String stage,
                       int indent,
                       String endStr,
                       String counter) {
    super(stage, indent);
    this.endStr  = endStr;
    if (endStr.equals("NO_MESSAGE")) {
      this.append("SELECT @flag = COUNT (*) FROM " + counter + ";");
    } else {
      this.append("SET @flag = @flag - 1");
    }
    this.append("END", indent - 1);
    this.sql = this.sb.toString();
  }

  /**
   * @brief Get the termination condition.
   * @return The termination condition.
   */
  public String getEndStr() {
    return endStr;
  }
}
