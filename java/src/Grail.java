/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 * 
 * See the file CREDITS.txt in the root directory for details.
 **/

import java.util.ArrayList;
import java.util.HashMap;

import Block.Block;

/**
 * @brief This class is used to generate T-SQL for graph processing programs.
 * Example:
 * // The value type of the vertex. It should be a type supported by the
 * // RDBMS, such as INT, FLOAT, etc.
 * VertexValType : INT
 * // The value type of the message.
 * MessageValType : INT
 * // The initial value of the vertices.
 * InitiateVal : 0
 * // The initial messages. We can either send to all the vertices using (ALL,
 * // value) or (some_vertex_id, value).
 * InitialMessage : (ALL,0)
 * // The way to do aggregation on messages. The message will automatically
 * // grouped on the destination vertex id. The aggregation should be supported
 * // by the RDBMS. It can be MIN, MAX, or UDAF (Please first define the UDAF).
 * CombineMessage: MIN(message) * 2
 * // The UpdateAndSend part can be combination of mutate values, send messages
 * // and flow control.
 * UpdateAndSend:
 * // Generate a variable called update. getVal() returns the current value of
 * // the vertex, which is stored in the table next.
 * update = cur.val < getVal()
 * // Flow control.
 * if (update) {
 *   // Mutate value.
 *   setVal(getVal())
 *   // Send messages. The first argument is the sending direction, which could
 *   // be all, in, out. The second argument is the message value.
 *   send(all, getVal()/out_cnts)
 * }
 * // The iteration control, it can be either NO_MESSAGE (Terminate when there
 * // are no messages left) or (ITER, max_iteration_num) (Terminate when the
 * // iteration number reaches upper bound.)
 * End: NO_MESSAGE
 */
public class Grail {
  // Grail program file path.
  private String filename = null;
  // SQL blocks.
  ArrayList<Block> blocks = null;

  /**
   * @brief Constructor. It will parse the configuration file and record
   * options.
   * @param filename The path to configuration file.
   */
  public Grail (String filename) {
    this.filename = filename;
  }

  /**
   * @brief Return SQL blocks.
   * @return SQL blocks.
   */
  public ArrayList<Block> getBlocks() {
    return this.blocks;
  }

  /**
   * @brief Run the Grail. It will first parse the input file of user, then
   * translate it into basic SQL blocks. After translation, possible
   * optimization will be added by modifying the basic SQL blocks.
   */
  public void run() {
    HashMap<String, String> options = Parser.parse(filename);
    Translator translator = new Translator(options);
    translator.translate();
    this.blocks = translator.getBlocks();
    Optimizer op = new Optimizer(translator.getConvertedOptions(),
                                 this.blocks,
                                 translator.getSenders());
    op.run();
  }

  /**
   * @brief Main function.
   * @param args The argument number can be either 0 (use config.grail as
   * default) or 1 (input file name).
   */
  public static void main(String[] args) {
    Grail grail = null;
    assert(args.length <= 1);
    if (args.length == 1) {
      grail = new Grail(args[0]);
    } else {
      grail = new Grail("config.grail");
    }
    grail.run();
    for (Block block : grail.getBlocks()) {
      block.print();
    }
  }

}
