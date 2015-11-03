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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @brief A reader to handle user input file. It reads the user file into
 * memory. The user can get next line in the file by calling getFirst() and
 * move to next line by calling rmFirst().
 */
public class FileCacheReader {
  // Buffered lines.
  ArrayList<String> buffer = new ArrayList<String>();
  // Current read pointer in the buffer.
  int curIdx = 0;

  /**
   * @brief Constructor.
   * @param filename The input file name.
   */
  public FileCacheReader(String filename) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(
          filename)));
      String s;
      while (( s = br.readLine()) != null) {
        // Skip all the empty lines.
        s = s.trim();
          if (s.equals("")) continue;
        buffer.add(s);
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @brief Read next line from the input stream.
   * @return The next line.
   */
  public String getFirst() {
    if (curIdx < buffer.size()) {
      return buffer.get(curIdx);
    } else {
      return null;
    }
  }

  /**
   * @brief Shift read pointer.
   */
  void rmFirst() {
    ++curIdx;
  }
}
