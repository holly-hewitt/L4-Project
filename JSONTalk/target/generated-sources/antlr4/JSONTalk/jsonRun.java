package JSONTalk;

import java.io.IOException;
import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class jsonRun {


	static CommonTokenStream lex(String filename) {
		try {
			jsonLexer lexer = new jsonLexer(CharStreams.fromFileName(filename));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			return tokens;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	static ParseTree parse(CommonTokenStream tokens) {
		jsonParser parser = new jsonParser(tokens);
		ParseTree tree = parser.json();
		return tree;
	}

	static String describe(CommonTokenStream tokens, ParseTree tree, boolean topLevel, boolean objects, boolean full,
			int depth, boolean nesting) {
		jsonDescriptorVisitor<?> descriptor = new jsonDescriptorVisitor<>();
		descriptor.visit(tree);
		String finalDescription = "";
		Collection<jsonComplexElement> elements = jsonDescriptorVisitor.ctxElems.values();
		
		descriptionLevel nestingOrNot;
		
		if (nesting) {
			nestingOrNot = descriptionLevel.NESTING;
		}else {
			nestingOrNot = descriptionLevel.NO_NESTING;
		}

		if (topLevel) {
			System.out.println("\nTop level description: \n");
			finalDescription += "\nTop level description: ";
			finalDescription += generateDescription1(descriptionLevel.TOPLEVEL,nestingOrNot, elements, depth);
		}

		if (objects) {
			System.out.println("\nDescription including object and array details: \n");
			finalDescription += "\nDescription including object and array details: ";
			finalDescription += generateDescription1(descriptionLevel.COMPLEXELEMENTS,nestingOrNot, elements, depth);
		}

		if (full) {
			System.out.println("\nFull description: \n");
			finalDescription += "\nFull description: ";
			finalDescription += generateDescription1(descriptionLevel.FULL,nestingOrNot, elements, depth);
		}
		

		return finalDescription;

		// TextToSpeech.SpeakString(finalDescription);
	}

	private static String generateDescription1(descriptionLevel l, descriptionLevel n, Collection<jsonComplexElement> x, int depth) {
		String description = "";
		for (jsonComplexElement object : x) {
			if (!object.elementDescription(l, n).equals("")) {
				if (object.getDepth() <= depth) {
					System.out.println(object.elementDescription(l, n));
					description += "\n" + object.elementDescription(l, n);
				}

			}
			if (l == descriptionLevel.TOPLEVEL) {
				break;
			}
		}
		return description;
	}

}