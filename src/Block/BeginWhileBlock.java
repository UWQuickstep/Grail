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
 * @brief Begin while block.
 *
 */
public class BeginWhileBlock extends Block {

  /**
   * @brief Constructor.
   * @param stage The string indicates the stage of this code block.
   * @param indent The indent level.
   * @param endStr The string indicates the termination condition of the
   *               iterations.
   */
  public BeginWhileBlock(String stage, int indent, String endStr) {
    super(stage, indent);
    String initVal = "-1";
    if (!endStr.equals("NO_MESSAGE")) {
      initVal = endStr.substring(endStr.indexOf('(') + 1,
                                 endStr.indexOf(')'))
                      .split(",")[1].trim();
    }
    this.append("DECLARE @flag int");
    this.append("SET @flag = " + initVal);
    this.append("WHILE @flag != 0");
    this.append("BEGIN");
    this.sql = this.sb.toString();
  }

}
