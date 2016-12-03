package com.fs.app.portal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Stack;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.Node;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class ParseHtml {
	private final String CONTENT;

	private Parser parser;

	private PrototypicalNodeFactory factory;

	private Stack<TagNode> nodeStack;

	private int subLength;

	private int textLength = 0;

	private int pos = 0;

	public ParseHtml(String content) {

		CONTENT = content;

		parser = Parser.createParser(content, "GBK");

		factory = new PrototypicalNodeFactory();

		factory.registerTag(new StrongTag());

		parser.setNodeFactory(factory);

		nodeStack = new Stack<TagNode>();

	}

	private void recusive(NodeIterator iterator) throws ParserException {

		while (iterator.hasMoreNodes()) {

			Node node = iterator.nextNode();

			if (node instanceof TagNode) {

				TagNode tagNode = (TagNode) node;

				Tag tag = tagNode.getEndTag();

				if (tag != null) {

					nodeStack.push(tagNode);

				}
			} else if (node instanceof TextNode) {

				if (node.getText().trim().length() == 0) {

					continue;
				}
				String nodeText = node.getText();

				int tLen = nodeText.length();

				if ((textLength < subLength)
						&& ((textLength + tLen) > subLength)) {

					pos = node.getStartPosition() + subLength - textLength;

					textLength = subLength;

					return;
				} else {

					textLength += tLen;

					pos = node.getEndPosition();

				}
			}
			if (node.getChildren() == null) {

				continue;
			}

			recusive(node.getChildren().elements());

			if (subLength <= textLength) {

				return;
			}
		}

	}

	public String subString(int length, String end) {

		if (length >= CONTENT.length() || length <= 0) {

			return CONTENT;

		}
		subLength = length;

		try {
			recusive(parser.elements());

		} catch (ParserException e) {

			System.out.println("parser error:" + e.getMessage());

			return CONTENT;

		}
		int size = nodeStack.size();

		StringBuffer buffer = new StringBuffer();

		buffer.append(CONTENT.substring(0, pos));

		while (size > 0) {

			TagNode node = nodeStack.pop();

			size--;
			if (node.getEndTag().getEndPosition() <= pos
					|| node.getTagBegin() >= pos) {

				continue;
			}

			buffer.append("</");

			buffer.append(node.getTagName());

			buffer.append(">");

		}
		buffer.append(end);

		return buffer.toString();

	}

	private static String getContent() {

		byte[] con = null;

		InputStream in = ParseHtml.class.getResourceAsStream("content.txt");

		try {
			int length = in.available();

			con = new byte[length];

			in.read(con, 0, length);

		} catch (IOException e) {

			e.printStackTrace();

		}
		try {
			return new String(con, "GBK");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

			return "";

		}
	}

	public static void main(String[] args) {

		String content = getContent();

		ParseHtml app = new ParseHtml(content);

		String str = app.subString(200, "");

		System.out.println(str);

	}
}

class StrongTag extends CompositeTag {

	private static final long serialVersionUID = 1L;

	private static final String[] mIds = new String[] { "STRONG" };

	private static final String[] mEndTagEnders = new String[] { "BODY", "HTML" };

	public String[] getIds() {

		return mIds;

	}

	public String[] getEndTagEnders()

	{
		return (mEndTagEnders);

	}
}
