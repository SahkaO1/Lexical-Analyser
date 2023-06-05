import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class LexicalAnalyser {

	public static List<Token> analyse(String input) throws NumberException, ExpressionException {

        ArrayList<Token> list= new ArrayList<Token>();
        String numberCharBuffer = new String();
		
		for (int i=0; i<input.length(); i++){

			Token tkn;
			char c = input.charAt(i);
	        
			if (Token.typeOf(c) == Token.TokenType.NUMBER || Token.typeOf(c) == Token.TokenType.NONE){
				
				if (Token.typeOf(c) == Token.TokenType.NUMBER){
					numberCharBuffer += c;
				}

				if (c == '.'){
					if(numberCharBuffer.equals("0") == true){
						numberCharBuffer += c;
					}

					else if (numberCharBuffer.equals("0") == false){
						throw new NumberException();
					}

					if (i == input.length()-1){
						throw new NumberException();
					}

					if (Token.typeOf(input.charAt(i+1)) != Token.TokenType.NUMBER){
						throw new NumberException();
					}
				}

				if (c == ' '){
					if (i == 0){
						if (Token.typeOf(input.charAt(i+1)) == Token.TokenType.PLUS ||
						Token.typeOf(input.charAt(i+1)) == Token.TokenType.MINUS ||
						Token.typeOf(input.charAt(i+1)) == Token.TokenType.DIVIDE ||
						Token.typeOf(input.charAt(i+1)) == Token.TokenType.TIMES){
							throw new ExpressionException();
						}
					}

					if (i != 0 && i != input.length()-1){
						if (Token.typeOf(input.charAt(i-1)) == Token.TokenType.NUMBER && Token.typeOf(input.charAt(i+1)) == Token.TokenType.NUMBER ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.PLUS && Token.typeOf(input.charAt(i+1)) == Token.TokenType.PLUS ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.MINUS && Token.typeOf(input.charAt(i+1)) == Token.TokenType.MINUS ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.DIVIDE && Token.typeOf(input.charAt(i+1)) == Token.TokenType.DIVIDE ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.TIMES && Token.typeOf(input.charAt(i+1)) == Token.TokenType.TIMES){
							throw new ExpressionException();
						}
					}

					if (i == input.length()-1){
						if (Token.typeOf(input.charAt(i-1)) == Token.TokenType.PLUS ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.MINUS ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.DIVIDE ||
						Token.typeOf(input.charAt(i-1)) == Token.TokenType.TIMES){
							throw new ExpressionException();
						}
					}

				}

				if (Token.typeOf(c) == Token.TokenType.NONE){
					if (c != '.' && c != ' '){
						throw new ExpressionException();
					}
				}

			}

			else if (Token.typeOf(c) == Token.TokenType.PLUS ||
			Token.typeOf(c) == Token.TokenType.MINUS ||
			Token.typeOf(c) == Token.TokenType.DIVIDE ||
			Token.typeOf(c) == Token.TokenType.TIMES){

				if (Token.typeOf(input.charAt(0)) != Token.TokenType.NUMBER){
					throw new ExpressionException();
				}

				if (i == input.length()-1){
					throw new ExpressionException();
				}

				if (Token.typeOf(input.charAt(i-1)) == Token.TokenType.PLUS || 
				Token.typeOf(input.charAt(i-1)) == Token.TokenType.MINUS || 
				Token.typeOf(input.charAt(i-1)) == Token.TokenType.DIVIDE || 
				Token.typeOf(input.charAt(i-1)) == Token.TokenType.TIMES){
					throw new ExpressionException();
				}

				if (numberCharBuffer == ""){
					throw new ExpressionException();
				}

				else {

					double val = Double.parseDouble(numberCharBuffer);
					tkn = new Token(val);
					list.add(tkn);

					tkn = new Token(Token.typeOf(c));
					list.add(tkn);
					numberCharBuffer = "";

				}
			
			}
            //last number logic
			if (i == input.length()-1){

				if (numberCharBuffer == ""){
					throw new ExpressionException();
				}

				else{

					double val = Double.parseDouble(numberCharBuffer);
					tkn = new Token(val);
					list.add(tkn);
				
				}

			}
		}

		return list;

	}

}
