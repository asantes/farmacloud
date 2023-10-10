package com.farmacloud.server.utiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.farmacloud.shared.model.infoView.MedicamentoSuggestion;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

public class MyMultiOracleHelper 
{
	private List<String> baseData = new ArrayList<String>();
	private static final char WHITESPACE_CHAR = ' ';
	private static final String WHITESPACE_STRING = " ";
	
	/**
	   * Constructor for <code>MultiWordSuggestOracle</code>. This uses a space as
	   * the whitespace character.
	   *
	   * @see #MultiWordSuggestOracle(String)
	   */
	  public MyMultiOracleHelper() {
	    this(" ");
	  }

	  /**
	   * Constructor for <code>MultiWordSuggestOracle</code> which takes in a set of
	   * whitespace chars that filter its input.
	   * <p>
	   * Example: If <code>".,"</code> is passed in as whitespace, then the string
	   * "foo.bar" would match the queries "foo", "bar", "foo.bar", "foo...bar", and
	   * "foo, bar". If the empty string is used, then all characters are used in
	   * matching. For example, the query "bar" would match "bar", but not "foo
	   * bar".
	   * </p>
	   *
	   * @param whitespaceChars the characters to treat as word separators
	   */
	  public MyMultiOracleHelper(String whitespaceChars) {
	    this.whitespaceChars = new char[whitespaceChars.length()];
	    for (int i = 0; i < whitespaceChars.length(); i++) {
	      this.whitespaceChars[i] = whitespaceChars.charAt(i);
	    }
	  }
	
	  /**
	   * Regular expression used to collapse all whitespace in a query string.
	   */
	  private static final String NORMALIZE_TO_SINGLE_WHITE_SPACE = "\\s+";
	  
	  /**
	   * The whitespace masks used to prevent matching and replacing of the given
	   * substrings.
	   */
	  private char[] whitespaceChars;
	  
   /*
	* Comparator used for sorting candidates from search.
	*/
	private Comparator<String> comparator = null;
	
	  /**
	   * Adds a suggestion to the oracle. Each suggestion must be plain text.
	   *
	   * @param suggestion the suggestion
	   */
	  public void add(String suggestion) 
	  {
	    String candidate = normalizeSuggestion(suggestion);
	    baseData.add(candidate);
	  }

	  /**
	   * Removes all of the suggestions from the oracle.
	   */
	  public void clear() {
		  baseData.clear();
	  }
	  
	  public boolean isEmpty(){
		  return baseData.isEmpty();
	  }
	  
	  /**
	   * Adds all suggestions specified. Each suggestion must be plain text.
	   *
	   * @param collection the collection
	   */
	  public final void addAll(Collection<String> collection) {
	    for (String suggestion : collection) {
	      add(suggestion);
	    }
	  }
	
	  public List<MultiWordSuggestion> requestSuggestions(Request request)
	  {
		  	List<MultiWordSuggestion> suggestions = null;
		    String query = normalizeSearch(request.getQuery());
		    int limit = request.getLimit();

		    // Get candidates from search words.
		    List<String> candidates = createCandidatesFromSearch(query, limit-1);

		    if(!candidates.isEmpty())
		    {
			    // Respect limit for number of choices.
			    int numberTruncated = Math.max(0, candidates.size() - limit);
			    for (int i = candidates.size() - 1; i > limit; i--) {
			      candidates.remove(i);
			    }
			    
			    // Convert candidates to suggestions.
			    suggestions = convertToFormattedSuggestions(query, candidates);
		    }
		    else
		    {
		    	MultiWordSuggestion m = new MultiWordSuggestion("", "No se ha encontrado ninguna coincidencia");
		    	suggestions = new ArrayList<MultiWordSuggestion>();
		    	suggestions.add(m);
		    }

		    return suggestions;
		    
		    //Response response = new Response(suggestions);
		   //response.setMoreSuggestionsCount(numberTruncated);
	  }

  /**
   * Normalize the search key by making it lower case, removing multiple spaces,
   * apply whitespace masks, and make it lower case.
   */
	  private String normalizeSearch(String search) 
	  {
		  // Use the same whitespace masks and case normalization for the search
		  // string as was used with the candidate values.
		  search = normalizeSuggestion(search);
	
		  // Remove all excess whitespace from the search string.
		  search = search.replaceAll(NORMALIZE_TO_SINGLE_WHITE_SPACE,WHITESPACE_STRING);
	
	    return search.trim();
	  }
	  
	  /**
	   * Takes the formatted suggestion, makes it lower case and blanks out any
	   * existing whitespace for searching.
	   */
	  private String normalizeSuggestion(String formattedSuggestion) 
	  {
	    // Formatted suggestions should already have normalized whitespace. So we
	    // can skip that step.

	    // Lower case suggestion.
	    formattedSuggestion = formattedSuggestion.toLowerCase();

	    // Apply whitespace.
	    if (whitespaceChars != null) {
	      for (int i = 0; i < whitespaceChars.length; i++) {
	        char ignore = whitespaceChars[i];
	        formattedSuggestion = formattedSuggestion.replace(ignore,
	            WHITESPACE_CHAR);
	      }
	    }
	    return formattedSuggestion;
	  }
	  
    /*
     * Find the sorted list of candidates that are matches for the given query.
	*/
	private List<String> createCandidatesFromSearch(String query, int limit) 
	{
		boolean multiple=false;
		ArrayList<String> candidates = new ArrayList<String>();
	
	    if (query.length() == 0) {
	      return candidates;
	    }
	
	    // Find all words to search for.
	    String[] searchWords = query.split(WHITESPACE_STRING);
	    if(searchWords.length>1)
	    	multiple = true;
	    
	    ConcurrentSkipListSet<String> candidateSet = null;
	    for (int i = 0; i < searchWords.length; i++)
	    {
	    	String word = searchWords[i];
	
	    	// Eliminate bogus word choices.
	    	if (word.length() == 0 || word.matches(WHITESPACE_STRING)) {
	    		continue;
	    	}
	
	    // Find the set of candidates that are associated with all the
	    // searchWords.
	    ConcurrentSkipListSet<String> thisWordChoices = createCandidatesFromWord(word, limit, multiple);
	    if (candidateSet == null) {
	    	candidateSet = thisWordChoices;
	    } else {
	    	candidateSet.retainAll(thisWordChoices);
	
	    if (candidateSet.size() < 2) {
	    	// If there is only one candidate, on average it is cheaper to
	    	// check if that candidate contains our search string than to
	    	// continue intersecting suggestion sets.
	          break;
	        }
	      }
	    }
	    if (candidateSet != null) {
	      candidates.addAll(candidateSet);
	     // Collections.sort(candidates, comparator);
	    }
	    return candidates;
	}
	
	  /**
	   * Creates a set of potential candidates that match the given query.
	   *
	   * @param query query string
	   * @return possible candidates
	   */
	  private ConcurrentSkipListSet<String> createCandidatesFromWord(String query, int limit, boolean multiple) 
	  {
		  ConcurrentSkipListSet<String> candidateSet = new ConcurrentSkipListSet<String>();
		  HashSet<String> greats = new HashSet<String>();
		  HashSet<String> goods = new HashSet<String>();
		  
		  if(multiple)
		  {
			  System.out.println("multiple");
			  for (String s: baseData) 
			  {
				  String[] words = s.split(WHITESPACE_STRING);
				  for(int i=0; i<words.length; i++)
					  if(words[i].startsWith(query))
					  {
						 greats.add(s);
						 break;
					  }
			  }
			  
			  candidateSet.addAll(greats);
		  }
		  else
		  {
			  for (String s: baseData) 
			  {
				  if(greats.size()<limit)
				  {
					  String[] words = s.split(WHITESPACE_STRING);
					  for(int i=0; i<words.length; i++)
						  if(words[i].startsWith(query))
						  {
							  if(i==0){
								  greats.add(s);
								  break;
							  }
							  else
								  goods.add(s);
						  }
				  }
				  else 
					  break;
			  }
			  
			  candidateSet.addAll(greats);
			  if(candidateSet.size()<limit)
				  candidateSet.addAll(goods);
		  }

		  return candidateSet;
	  }

	  /**
	   * Returns real suggestions with the given query in <code>strong</code> html
	   * font.
	   *
	   * @param query query string
	   * @param candidates candidates
	   * @return real suggestions
	   */
	  private List<MultiWordSuggestion> convertToFormattedSuggestions(String query,List<String> candidates) 
	  {
	    List<MultiWordSuggestion> suggestions = new ArrayList<MultiWordSuggestion>();

	    for (int i = 0; i < candidates.size(); i++) 
	    {
	      String candidate = candidates.get(i);
	      int cursor = 0;
	      int index = 0;
	      // Use real suggestion for assembly.
	      String formattedSuggestion = candidate; //Aqui lo asignaba a un Mapa que no entiendo que hacia.

	      // Create strong search string.
	      SafeHtmlBuilder accum = new SafeHtmlBuilder();

	      String[] searchWords = query.split(WHITESPACE_STRING);
	      while (true) 
	      {
	        WordBounds wordBounds = findNextWord(candidate, searchWords, index);
	        if (wordBounds == null) {
	          break;
	        }
	        if (wordBounds.startIndex == 0 ||
	            WHITESPACE_CHAR == candidate.charAt(wordBounds.startIndex - 1))
	        {
	          /* Sera sugerencia si alguna de las palabras empiezan igual que lo introducido por teclado
	           * Por ejemplo: si introducimos "ve"
	           *                     
	           *                      verstrynge si sera mostrado
	           *                      bienvenidos NO sera mostrado
	           */
	          String part1 = formattedSuggestion.substring(cursor, wordBounds.startIndex);
	          String part2 = formattedSuggestion.substring(wordBounds.startIndex, wordBounds.endIndex);
	          cursor = wordBounds.endIndex;
	          accum.appendEscaped(part1);
	          accum.appendHtmlConstant("<strong>");
	          accum.appendEscaped(part2);
	          accum.appendHtmlConstant("</strong>");
	        }
	        index = wordBounds.endIndex;
	      }

	      // Check to make sure the search was found in the string.
	      if (cursor == 0) {
	        continue;
	      }

	      accum.appendEscaped(formattedSuggestion.substring(cursor));
	      MultiWordSuggestion suggestion = createSuggestion(formattedSuggestion, accum.toSafeHtml().asString());
	      suggestions.add(suggestion);
	    
	    }
	    return suggestions;
	  }
	  
	  /**
	   * Creates the suggestion based on the given replacement and display strings.
	   *
	   * @param replacementString the string to enter into the SuggestBox's text box
	   *          if the suggestion is chosen
	   * @param displayString the display string
	   *
	   * @return the suggestion created
	   */
	  protected MultiWordSuggestion createSuggestion(String replacementString, String displayString) {
	    return new MultiWordSuggestion(replacementString, displayString);
	  }
	  
	  /**
	   * Returns a {@link WordBounds} representing the first word in {@code
	   * searchWords} that is found in candidate starting at {@code indexToStartAt}
	   * or {@code null} if no words could be found.
	   */
	  private WordBounds findNextWord(String candidate, String[] searchWords, int indexToStartAt)
	  {
	    WordBounds firstWord = null;
	    for (String word : searchWords) 
	    {
	      int index = candidate.indexOf(word, indexToStartAt);
	      if (index != -1) 
	      {
	        WordBounds newWord = new WordBounds(index, word.length());
	        if (firstWord == null || newWord.compareTo(firstWord) < 0) {
	          firstWord = newWord;
	        }
	      }
	    }
	    return firstWord;
	  }
	  
	  /**
	   * A class reresenting the bounds of a word within a string.
	   *
	   * The bounds are represented by a {@code startIndex} (inclusive) and
	   * an {@code endIndex} (exclusive).
	   */
	  private static class WordBounds implements Comparable<WordBounds> {

	    final int startIndex;
	    final int endIndex;

	    public WordBounds(int startIndex, int length) {
	      this.startIndex = startIndex;
	      this.endIndex = startIndex + length;
	    }

	    public int compareTo(WordBounds that) {
	      int comparison = this.startIndex - that.startIndex;
	      if (comparison == 0) {
	        comparison = that.endIndex - this.endIndex;
	      }
	      return comparison;
	    }
	  }
	
	
}
