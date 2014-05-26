
package edu.buffalo.cse562;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeSet;

import jdbm.PrimaryTreeMap;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;

// This class is used for performing the selection operation on a given table
public class WhereOperation {

public static ArrayList<String> indexSelection(ArrayList<Expression> nonJoinExp, HashMap<String, List> tablesNameAndIndexesMap, HashMap<String,HashMap<String,PrimaryTreeMap<String, ArrayList<String>>>> tablesNameAndBTreeMap, boolean isTpch7,boolean isTpch12){
		
	//System.out.println(isTpch12);
	if(isTpch12){
		return null;
	}
	
	//System.out.println("IN INDEX SELECTION");
		ArrayList<String> resultTable = null;
		//int count=0;
		ArrayList<String> rightExpressions=new ArrayList<String>(2);
		
		
		String column = null;
		String table =  null;
		String[] date = new String[2]; 
		
		
		for(Expression exp:nonJoinExp)
		{
		String leftVal=null,rightVal=null,leftExp = null;
		String leftTable = null;
		//String rightTable = null;
		List<Index> tableIndexSchema=new ArrayList<Index>(); 
		//ArrayList<String> indexColumnNames= new ArrayList<String>();
		
		
		if(exp instanceof GreaterThan)
		{
			leftVal = ((GreaterThan) exp).getLeftExpression().toString();
			rightVal = ((GreaterThan)exp).getRightExpression().toString();
			if(leftVal.contains(".")){
						leftTable=leftVal.split("\\.")[0];
						leftExp = leftVal.split("\\.")[1];
			}
			if(!(rightVal.contains("."))){
				tableIndexSchema=tablesNameAndIndexesMap.get(leftTable);
				if(tableIndexSchema.size()>1)
				{
					for(Index i:tableIndexSchema)
					{
						if(i.getColumnsNames().contains(leftExp))
								{
									rightExpressions.add(rightVal);
									//count++;
									date[0] = rightVal.split("\\'")[1];
								}
						/*else{
							return null;
						}*/
					}
				
									
			}

		}
		}
		else if(exp instanceof GreaterThanEquals)
		{
			leftVal = ((GreaterThanEquals) exp).getLeftExpression().toString();
			rightVal = ((GreaterThanEquals)exp).getRightExpression().toString();
			if(leftVal.contains(".")){
						leftTable=leftVal.split("\\.")[0];
						leftExp = leftVal.split("\\.")[1];
			}
			if(!(rightVal.contains("."))){
				tableIndexSchema=tablesNameAndIndexesMap.get(leftTable);
				if(tableIndexSchema.size()>1)
				{
					for(Index i:tableIndexSchema)
					{
						if(i.getColumnsNames().contains(leftExp))
								{
							//(leftTable + "." + i.getName() + ".indexkey"); // the index name
								//idx.get(rightExp);
									rightExpressions.add(rightVal);
									//count++;
									date[0] = rightVal.split("\\'")[1];
								}
						/*else{
							return null;
						}*/
					}
				
			}
					
			}

		}
		else if(exp instanceof MinorThan)
		{
			leftVal = ((MinorThan) exp).getLeftExpression().toString();
			rightVal = ((MinorThan)exp).getRightExpression().toString();
			if(leftVal.contains(".")){
						leftTable=leftVal.split("\\.")[0];
						leftExp = leftVal.split("\\.")[1];
			}
			if(!(rightVal.contains("."))){
				tableIndexSchema=tablesNameAndIndexesMap.get(leftTable);
				if(tableIndexSchema.size()>1)
				{
					for(Index i:tableIndexSchema)
					{
						if(i.getColumnsNames().contains(leftExp))
								{
							//(leftTable + "." + i.getName() + ".indexkey"); // the index name
								//idx.get(rightExp);
									rightExpressions.add(rightVal);
									//count++;
									date[1] = rightVal.split("\\'")[1];
								}
						/*else{
							return null;
						}*/
					}
					
			}
					
			}
		}
		else if(exp instanceof MinorThanEquals)
		{
			leftVal = ((MinorThanEquals) exp).getLeftExpression().toString();
			rightVal = ((MinorThanEquals)exp).getRightExpression().toString();
			if(leftVal.contains(".")){
						leftTable=leftVal.split("\\.")[0];
						leftExp = leftVal.split("\\.")[1];
			}
			if(!(rightVal.contains("."))){
					
				tableIndexSchema=tablesNameAndIndexesMap.get(leftTable);
				if(tableIndexSchema.size()>1)
				{
					for(Index i:tableIndexSchema)
					{
						if(i.getColumnsNames().contains(leftExp))
								{
							//(leftTable + "." + i.getName() + ".indexkey"); // the index name
								//idx.get(rightExp);
									rightExpressions.add(rightVal);
									//count++;
									date[1] = rightVal.split("\\'")[1];
								}
						/*else{
							return null;
						}*/
					}
					
			}
			}
		}
		
		//System.out.println(tableIndexSchema);
		//System.out.println("column");;
			//System.out.println(leftExp);
			column = leftExp;
			table = leftTable;
		}
		//System.out.println(rightExpressions.get(0)+"---"+rightExpressions.get(1));
		//System.out.println("--------------------");
		int index = -1;
		for(int i=0;i<tablesNameAndIndexesMap.get(table).size();i++){
			//System.out.println(col.toString());
			if(tablesNameAndIndexesMap.get(table).get(i).toString().contains(column)){
				index = i; 
				break;
			}
		}
		if(index != -1){
			
			resultTable = new ArrayList<String>();
		}
		else{
			//System.out.println("returning null");
			return resultTable;
		}
		String indexName = tablesNameAndIndexesMap.get(table).get(index).toString();
		//System.out.println("indexName: "+indexName);
		String[] name = indexName.split("\\ ");
		
		String indexKey = (table + "." + name[2] + ".indexkey");
		//System.out.println("+++++++++++++");
		//System.out.println(tablesNameAndBTreeMap.get(table));
		//System.out.println("----------------------");
		for(Entry<String, HashMap<String, PrimaryTreeMap<String, ArrayList<String>>>> etr:tablesNameAndBTreeMap.entrySet()){
			//System.out.println(etr.getKey());
		//	System.out.println(etr.getValue().keySet());
		}
		
		
		PrimaryTreeMap<String, ArrayList<String>> indexMap = tablesNameAndBTreeMap.get(table).get(indexKey);
		//System.out.println(indexKey);
		//System.out.println("========");
		
		//System.out.println(indexMap);
		//System.out.println("==============");
		//System.out.println(rightExpressions.get(0));
		//System.out.println(rightExpressions.get(1));
		//System.out.println("==========");
		int size = 0;
		//ArrayList<String> resultantList = new ArrayList<String>();
		//System.out.println(date[0]);
		//System.out.println(date[1]);
		//System.out.println("==============");
		//System.out.println(indexMap);
		for(Entry<String, ArrayList<String>> etr:indexMap.entrySet()){
		//	System.out.println("table--"+etr.getKey());
		}
		for(String key:indexMap.keySet()){
			//System.out.println(indexMap.get(key).size());
			
//			System.out.println(key);



			String minDate[] = date[0].split("-");
			String maxDate[] = date[1].split("-");
			String tupleDate[] = key.split("-");
				

			if (Integer.parseInt(tupleDate[0]) < Integer.parseInt(minDate[0])) {
				/*for(String str:indexMap.get(key)){
					System.out.println(str);}*/
			} else if (Integer.parseInt(tupleDate[0]) == Integer.parseInt(minDate[0])) {
				
				if (Integer.parseInt(tupleDate[1]) < Integer.parseInt(minDate[1])) {
				} else if (Integer.parseInt(tupleDate[1]) == Integer.parseInt(minDate[1])) {
					if (Integer.parseInt(tupleDate[2]) < Integer.parseInt(minDate[2])) {
					} else {
						if(isTpch7){
							if(maxEqualDateSatisfy(maxDate,tupleDate)){
								for(String str:indexMap.get(key)){
									resultTable.add(str);
								}
							}
						}else{
							if(maxDateSatisfy(maxDate,tupleDate)){
								for(String str:indexMap.get(key)){
									resultTable.add(str);
								}
							}
						}
						
					}
				} else {
					
					if(isTpch7){
						if(maxEqualDateSatisfy(maxDate,tupleDate)){
							for(String str:indexMap.get(key)){
								resultTable.add(str);
							}
						}
					}
					else{
						if(maxDateSatisfy(maxDate,tupleDate)){
							for(String str:indexMap.get(key)){
								resultTable.add(str);
							}
						}
					}
				}
			} else {
				
				if(isTpch7){
					if(maxEqualDateSatisfy(maxDate,tupleDate)){
						for(String str:indexMap.get(key)){
							resultTable.add(str);
						}
					}
				}else{
					if(maxDateSatisfy(maxDate,tupleDate)){
						for(String str:indexMap.get(key)){
							//System.out.println(str);
							resultTable.add(str);
						}
					}
				}
			}
			
			
			size += indexMap.get(key).size();
		}
		//System.out.println(resultTable.size());
		
		//System.out.println(indexMap.tailMap(rightExpressions.get(0)));//headMap(rightExpressions.get(1)));
		
		/*SortedMap<String,ArrayList<String>> tuplesMap = indexMap.tailMap(rightExpressions.get(0));
		System.out.println(tuplesMap);
		System.out.println(indexMap.headMap(rightExpressions.get(1)));*/
		//tuplesMap.headMap(rightExpressions.get(0));
		//System.out.println(tuplesMap.headMap(rightExpressions.get(1)));
		
		
		//ArrayList <String> tuples = indexMap.get(rightExpressions.get(1));
		
		//System.out.println("tuple "+tuple);
		/*for (String str: tablesNameAndBTreeMap.keySet()){
			System.out.println(tablesNameAndBTreeMap.get(str));
			System.out.println("\n\n");
		}*/
		
		//System.out.println(tablesNameAndIndexesMap.get(table).get(1));
		
		//indexName = (leftTable + "." + i.getName() + ".indexkey");
		//resultTable=indexMap.submap(rightExpressions.get(0),rightExpressions.get(1));
		//resultTable.add(indexName.get(rightExpressions(1));
		//System.out.println("returning.............");
	return resultTable;
	}

	private static boolean maxDateSatisfy(String[] maxDate,String[] tupleDate){

								
		if (Integer.parseInt(tupleDate[0]) > Integer.parseInt(maxDate[0])) {

		} else if (Integer.parseInt(tupleDate[0]) == Integer.parseInt(maxDate[0])) {
			if (Integer.parseInt(tupleDate[1]) > Integer.parseInt(maxDate[1])) {
			} else if (Integer.parseInt(tupleDate[1]) == Integer.parseInt(maxDate[1])) {
				if (Integer.parseInt(tupleDate[2]) >= Integer.parseInt(maxDate[2])) {
				} else {
					
					return true;
				}
			} else {
				
				return true;
			}
		} else {
			
			return true;
		}
		
		return false;
	
	}
	
	private static boolean maxEqualDateSatisfy(String[] maxDate,String[] tupleDate){

		
		if (Integer.parseInt(tupleDate[0]) > Integer.parseInt(maxDate[0])) {

		} else if (Integer.parseInt(tupleDate[0]) == Integer.parseInt(maxDate[0])) {
			if (Integer.parseInt(tupleDate[1]) > Integer.parseInt(maxDate[1])) {
			} else if (Integer.parseInt(tupleDate[1]) == Integer.parseInt(maxDate[1])) {
				if (Integer.parseInt(tupleDate[2]) > Integer.parseInt(maxDate[2])) {
				} else {
					
					return true;
				}
			} else {
				
				return true;
			}
		} else {
			
			return true;
		}
		
		return false;
	
	}
	
	
	public static void indexUpdation(ArrayList<Expression> nonJoinExp, HashMap<String, List> tablesNameAndIndexesMap, HashMap<String,HashMap<String,PrimaryTreeMap<String, ArrayList<String>>>> tablesNameAndBTreeMap, int columnIndex, String newValue,String tableName){
		
		//System.out.println("IN INDEX SELECTION");
			ArrayList<String> resultTable = null;
			//int count=0;
			ArrayList<String> rightExpressions=new ArrayList<String>(2);
			
			
			String column = null;
			String table =  null;
			String[] date = new String[2]; 
			
			
			for(Expression exp:nonJoinExp)
			{
				//System.out.println("in");
			String leftVal=null,rightVal=null;
			String leftTable = tableName;
			//String rightTable = null;
			List<Index> tableIndexSchema=new ArrayList<Index>(); 
			//ArrayList<String> indexColumnNames= new ArrayList<String>();
			
			
			if(exp instanceof GreaterThan)
			{
				//System.out.println(">");
				leftVal = ((GreaterThan) exp).getLeftExpression().toString();
				rightVal = ((GreaterThan)exp).getRightExpression().toString();
				/*if(leftVal.contains(".")){
							leftTable=leftVal.split("\\.")[0];
							leftExp = leftVal.split("\\.")[1];
				}*/
				if(!(rightVal.contains("."))){
					tableIndexSchema=tablesNameAndIndexesMap.get(leftTable.toLowerCase());
					if(tableIndexSchema.size()>1)
					{
						for(Index i:tableIndexSchema)
						{
							if(i.getColumnsNames().contains(leftVal.toLowerCase()))
									{
										rightExpressions.add(rightVal);
										//count++;
										date[0] = rightVal.split("\\'")[1];
									}
							/*else{
								return null;
							}*/
						}
					
										
				}

			}
			}
			else if(exp instanceof GreaterThanEquals)
			{
				//System.out.println("in >=");
				leftVal = ((GreaterThanEquals) exp).getLeftExpression().toString();
				rightVal = ((GreaterThanEquals)exp).getRightExpression().toString();
//				System.out.println(leftVal);
/*				if(leftVal.contains(".")){
							leftTable=leftVal.split("\\.")[0];
							leftExp = leftVal.split("\\.")[1];
				}
*/				if(!(rightVal.contains("."))){
					//System.out.println(leftTable);
					tableIndexSchema=tablesNameAndIndexesMap.get(leftTable.toLowerCase());
					if(tableIndexSchema.size()>1)
					{
						for(Index i:tableIndexSchema)
						{
							if(i.getColumnsNames().contains(leftVal.toLowerCase()))
									{
								//(leftTable + "." + i.getName() + ".indexkey"); // the index name
									//idx.get(rightExp);
										rightExpressions.add(rightVal);
										//count++;
										date[0] = rightVal.split("\\'")[1];
									}
							/*else{
								return null;
							}*/
						}
					
				}
						
				}

			}
			else if(exp instanceof MinorThan)
			{
				//System.out.println("<");
				leftVal = ((MinorThan) exp).getLeftExpression().toString();
				rightVal = ((MinorThan)exp).getRightExpression().toString();
				/*if(leftVal.contains(".")){
							leftTable=leftVal.split("\\.")[0];
							leftExp = leftVal.split("\\.")[1];
				}*/
				if(!(rightVal.contains("."))){
					tableIndexSchema=tablesNameAndIndexesMap.get(leftTable.toLowerCase());
					if(tableIndexSchema.size()>1)
					{
						for(Index i:tableIndexSchema)
						{
							if(i.getColumnsNames().contains(leftVal.toLowerCase()))
									{
								//(leftTable + "." + i.getName() + ".indexkey"); // the index name
									//idx.get(rightExp);
										rightExpressions.add(rightVal);
										//count++;
										date[1] = rightVal.split("\\'")[1];
									}
							/*else{
								return null;
							}*/
						}
						
				}
						
				}
			}
			else if(exp instanceof MinorThanEquals)
			{
				//System.out.println("<=");
				leftVal = ((MinorThanEquals) exp).getLeftExpression().toString();
				rightVal = ((MinorThanEquals)exp).getRightExpression().toString();
				//System.out.println(leftVal);
				//System.out.println(rightVal);
				/*if(leftVal.contains(".")){
							leftTable=leftVal.split("\\.")[0];
							leftExp = leftVal.split("\\.")[1];
				}*/
				if(!(rightVal.contains("."))){
						
					tableIndexSchema=tablesNameAndIndexesMap.get(leftTable.toLowerCase());
					if(tableIndexSchema.size()>1)
					{
						for(Index i:tableIndexSchema)
						{
							if(i.getColumnsNames().contains(leftVal.toLowerCase()))
									{
								//(leftTable + "." + i.getName() + ".indexkey"); // the index name
									//idx.get(rightExp);
										rightExpressions.add(rightVal);
										//count++;
										date[1] = rightVal.split("\\'")[1];
									}
							/*else{
								return null;
							}*/
						}
						
				}
				}
			}
			
			//System.out.println(tableIndexSchema);
			//System.out.println("column");;
				//System.out.println(leftExp);
				column = leftVal;
				table = leftTable;
			}
			//System.out.println(rightExpressions.get(0)+"---"+rightExpressions.get(1));
			//System.out.println("--------------------");
			//System.out.println(table);
			//System.out.println(tablesNameAndIndexesMap);
			int index = -1;
			for(int i=0;i<tablesNameAndIndexesMap.get(table.toLowerCase()).size();i++){
				//System.out.println(col.toString());
				if(tablesNameAndIndexesMap.get(table.toLowerCase()).get(i).toString().contains(column.toLowerCase())){
					index = i; 
					break;
				}
			}
			if(index != -1){
				
				resultTable = new ArrayList<String>();
			}
			else{
				//System.out.println("returning null");
				return;
			}
			String indexName = tablesNameAndIndexesMap.get(table.toLowerCase()).get(index).toString();
			//System.out.println("indexName: "+indexName);
			String[] name = indexName.split("\\ ");
			
			String indexKey = (table.toLowerCase() + "." + name[2] + ".indexkey");
			//System.out.println("+++++++++++++");
			//System.out.println(tablesNameAndBTreeMap.get(table));
			//System.out.println("----------------------");
			for(Entry<String, HashMap<String, PrimaryTreeMap<String, ArrayList<String>>>> etr:tablesNameAndBTreeMap.entrySet()){
				//System.out.println(etr.getKey());
			//	System.out.println(etr.getValue().keySet());
			}
			
			
			PrimaryTreeMap<String, ArrayList<String>> indexMap = tablesNameAndBTreeMap.get(table.toLowerCase()).get(indexKey);
			//System.out.println(indexKey);
			//System.out.println("========");
			
			//System.out.println(indexMap);
			//System.out.println("==============");
			//System.out.println(rightExpressions.get(0));
			//System.out.println(rightExpressions.get(1));
			//System.out.println("==========");
			int size = 0;
			//ArrayList<String> resultantList = new ArrayList<String>();
			//System.out.println(date[0]);
			//System.out.println(date[1]);
			//System.out.println("==============");
			//System.out.println(indexMap);
			for(Entry<String, ArrayList<String>> etr:indexMap.entrySet()){
			//	System.out.println("table--"+etr.getKey());
			}
			for(String key:indexMap.keySet()){
				//System.out.println(indexMap.get(key).size());
				
//				System.out.println(key);


				//System.out.println(date[0]);
				String minDate[] = date[0].split("-");
				String maxDate[] = date[1].split("-");
				String tupleDate[] = key.split("-");
					

				if (Integer.parseInt(tupleDate[0]) < Integer.parseInt(minDate[0])) {
					/*for(String str:indexMap.get(key)){
						System.out.println(str);}*/
				} else if (Integer.parseInt(tupleDate[0]) == Integer.parseInt(minDate[0])) {
					
					if (Integer.parseInt(tupleDate[1]) < Integer.parseInt(minDate[1])) {
					} else if (Integer.parseInt(tupleDate[1]) == Integer.parseInt(minDate[1])) {
						if (Integer.parseInt(tupleDate[2]) < Integer.parseInt(minDate[2])) {
						} else {
							/*if(isTpch7){
								if(maxEqualDateSatisfy(maxDate,tupleDate)){
									for(String str:indexMap.get(key)){
										resultTable.add(str);
									}
								}
							}else{*/
								if(maxDateSatisfy(maxDate,tupleDate)){
									ArrayList<String> list = indexMap.get(key);
									//System.out.println(list);
									ArrayList<String> newList = new ArrayList<>();
									for(String str:list){
										
										String[] tupleArr = str.split("\\|");
		                        		String newTuple = "";
		                        		for (int i=0;i<tupleArr.length;i++) {
		                        			if (i==columnIndex ){
		                        				
		                        				newTuple = newTuple+newValue+"|";
		                        			}
		                        			else {
		                        				newTuple = newTuple+tupleArr[i]+"|";
		                        			}
		                        			
		                        		}
										
										newList.add(newTuple.substring(0, newTuple.length()-2));
									}
									indexMap.put(key, newList);
					/*				System.out.println(list.size());
									System.out.println(newList.size());*/
									//System.out.println(newList);
								}
							//}
							
						}
					} else {
						
						/*if(isTpch7){
							if(maxEqualDateSatisfy(maxDate,tupleDate)){
								for(String str:indexMap.get(key)){
									resultTable.add(str);
								}
							}
						}
						else{*/
							if(maxDateSatisfy(maxDate,tupleDate)){
								ArrayList<String> list = indexMap.get(key);
								ArrayList<String> newList = new ArrayList<>();
								for(String str:list){
									
									String[] tupleArr = str.split("\\|");
	                        		String newTuple = "";
	                        		for (int i=0;i<tupleArr.length;i++) {
	                        			if (i==columnIndex ){
	                        				
	                        				newTuple = newTuple+newValue+"|";
	                        			}
	                        			else {
	                        				newTuple = newTuple+tupleArr[i]+"|";
	                        			}
	                        			
	                        		}
									
									newList.add(newTuple.substring(0, newTuple.length()-2));
								}
								indexMap.put(key, newList);
							}
						//}
					}
				} else {
					
					/*if(isTpch7){
						if(maxEqualDateSatisfy(maxDate,tupleDate)){
							for(String str:indexMap.get(key)){
								resultTable.add(str);
							}
						}
					}else{*/
						if(maxDateSatisfy(maxDate,tupleDate)){
							ArrayList<String> list = indexMap.get(key);
							ArrayList<String> newList = new ArrayList<>();
							for(String str:list){
								list.remove(str);
								String[] tupleArr = str.split("\\|");
                        		String newTuple = "";
                        		for (int i=0;i<tupleArr.length;i++) {
                        			if (i==columnIndex ){
                        				
                        				newTuple = newTuple+newValue+"|";
                        			}
                        			else {
                        				newTuple = newTuple+tupleArr[i]+"|";
                        			}
                        			
                        		}
								
								newList.add(newTuple.substring(0, newTuple.length()-2));
							}
							indexMap.put(key, newList);
						}
					//}
				}
				
				
				size += indexMap.get(key).size();
			}
			//System.out.println(resultTable.size());
			
			//System.out.println(indexMap.tailMap(rightExpressions.get(0)));//headMap(rightExpressions.get(1)));
			
			/*SortedMap<String,ArrayList<String>> tuplesMap = indexMap.tailMap(rightExpressions.get(0));
			System.out.println(tuplesMap);
			System.out.println(indexMap.headMap(rightExpressions.get(1)));*/
			//tuplesMap.headMap(rightExpressions.get(0));
			//System.out.println(tuplesMap.headMap(rightExpressions.get(1)));
			
			
			//ArrayList <String> tuples = indexMap.get(rightExpressions.get(1));
			
			//System.out.println("tuple "+tuple);
			/*for (String str: tablesNameAndBTreeMap.keySet()){
				System.out.println(tablesNameAndBTreeMap.get(str));
				System.out.println("\n\n");
			}*/
			
			//System.out.println(tablesNameAndIndexesMap.get(table).get(1));
			
			//indexName = (leftTable + "." + i.getName() + ".indexkey");
			//resultTable=indexMap.submap(rightExpressions.get(0),rightExpressions.get(1));
			//resultTable.add(indexName.get(rightExpressions(1));
			//System.out.println("returning.............");
		return;
		
	}
	
	
	
	
	
// this method will apply selection operations on the table given as input to the function
public static Table selectionOnTable(Expression expression, Table tableToApplySelectionOn) throws IOException, ParseException {

	
	/*for(String str: tableToApplySelectionOn.tableTuples)
	{
		System.out.println(str);
	}*/
//System.out.println(tableToApplySelectionOn.columnIndexMap);
// if the where expression is null then we don't apply any operations on the table and just return the table as is
if(expression == null)
return tableToApplySelectionOn;
// this Table contains the resultant table, obtained after applying selection operation
File resultantTableFile = new File(tableToApplySelectionOn.tableDataDirectoryPath+System.getProperty("file.separator") + tableToApplySelectionOn.tableName + "|.tbl");
if(!resultantTableFile.exists())
resultantTableFile.createNewFile();
Table resultantTable = new Table(tableToApplySelectionOn.tableName + "|", 
tableToApplySelectionOn.noOfColumns,
resultantTableFile,
tableToApplySelectionOn.tableDataDirectoryPath);

// initialize the column description list of the resultant table, which would be the same as the table given as input
resultantTable.columnDescriptionList = tableToApplySelectionOn.columnDescriptionList;
// initialize the column index map of the resultant table, which would be the same as the table given as input
resultantTable.columnIndexMap = tableToApplySelectionOn.columnIndexMap;

// we use the following conditions to evaluate the expressions
if (expression instanceof EqualsTo || expression instanceof GreaterThanEquals
|| expression instanceof GreaterThan || expression instanceof MinorThanEquals || expression instanceof MinorThan || expression instanceof NotEqualsTo) {
// indices of the tuples  that match the where clause or satisfy the where clause
ArrayList<Integer> listOfIndices = expressionEvaluator(expression, tableToApplySelectionOn);
populateTable(resultantTable, tableToApplySelectionOn, listOfIndices);

}

 else if (expression instanceof InExpression) {
} else if (expression instanceof LikeExpression) {
} else if (expression instanceof AndExpression) {
// this is the AND expression extracted from the WHERE clause
AndExpression andExpression = (AndExpression) expression;
// this is the left expression of the AND expression
Expression leftExpression = ((Expression) andExpression.getLeftExpression());
// this is the right expression of the AND expression
Expression rightExpression = ((Expression) andExpression.getRightExpression());
// this stores the list of indices that satisfy the leftExpression
ArrayList<Integer> leftArr = expressionEvaluator(leftExpression, tableToApplySelectionOn);
// this stores the list of indices that satisfy the rightExpression
ArrayList<Integer> rightArr = expressionEvaluator(rightExpression, tableToApplySelectionOn);
// indices of the tuples  that match the where clause or satisfy the where clause 
ArrayList<Integer> listOfIndices = new ArrayList<Integer>();
//System.out.println(leftExpression);
//System.out.println(leftArr);
//System.out.println(rightExpression);
//System.out.println(rightArr);
// the following nested loop is used to find the intersection of the two list indices
for (int i : leftArr) {
if (rightArr.contains(i)) {
listOfIndices.add(i);
}
}
// this is used to populate the resultant table with the indices in the listOfIndices
populateTable(resultantTable, tableToApplySelectionOn, listOfIndices);

} else if (expression instanceof OrExpression) {
OrExpression orExpression = (OrExpression) expression;
Expression leftExpression = ((Expression) orExpression.getLeftExpression());
Expression rightExpression = ((Expression) orExpression.getRightExpression());
// this stores the list of indices that satisfy the leftExpression
ArrayList<Integer> leftArr = expressionEvaluator(leftExpression, tableToApplySelectionOn);
// this stores the list of indices that satisfy the rightExpression
ArrayList<Integer> rightArr = expressionEvaluator(rightExpression, tableToApplySelectionOn);
// this set is used to store the list of indices of tuples that satisfy both left and right expression
TreeSet<Integer> set = new TreeSet<Integer>();
for (int i : leftArr) {
set.add(i);
}
for (int i : rightArr) {
set.add(i);
}

ArrayList<Integer> listOfIndices = new ArrayList<Integer>();

for (int i : set) {
listOfIndices.add(i);
}

populateTable(resultantTable, tableToApplySelectionOn, listOfIndices);

} else if (expression instanceof Between) {

} else if (expression instanceof NullValue) {

} else if (expression instanceof IsNullExpression) {

}
// change the name of the resultant table to be the name of the table on which you applied selection conditions, this is done to extract the join conditions
resultantTable.tableName = tableToApplySelectionOn.tableName;

return resultantTable;
}

// this method is used to evaluate expression recursively, it does logical, arithmetic and relational operations, and returns a list of indices that satisfy all the conditions
private static ArrayList<Integer> expressionEvaluator(Expression expression, Table tableToApplySelectionOn) throws IOException, ParseException {
// this is the actual list of indices that stores the indices of the tuples that satisfy all the conditions
ArrayList<Integer> listOfIndices = new ArrayList<Integer>();

// this Table contains the resultant table after applying selection operation
Table resultantTable = new Table(tableToApplySelectionOn);
resultantTable.tableName = "resultTable";

// the following conditions are to evaluate the EQUAL expression
if (expression instanceof EqualsTo) {
// this extracts the equal to clause in the WHERE clause
EqualsTo equalsExpression = (EqualsTo) expression;
// this extracts the left and the right expressions in the equal to clause
Expression leftExpression = ((Expression) equalsExpression.getLeftExpression());
Expression rightExpression = ((Expression) equalsExpression.getRightExpression());

if (leftExpression instanceof BinaryExpression || rightExpression instanceof BinaryExpression) {
listOfIndices = alegbricExpressionEvaluator(equalsExpression, tableToApplySelectionOn);

} else {
String leftVal = leftExpression.toString();
//System.out.println(leftVal);
String rightVal = rightExpression.toString();
//System.out.println(tableToApplySelectionOn);
//System.out.println(tableToApplySelectionOn.columnDescriptionList);
//System.out.println(tableToApplySelectionOn.columnIndexMap);
/*System.out.println(leftVal);
System.out.println(tableToApplySelectionOn.columnIndexMap.get(leftVal));
*/
String type = tableToApplySelectionOn.columnDescriptionList.get(tableToApplySelectionOn.columnIndexMap.get(leftVal)).getColDataType().getDataType();
//String tuple = null;
int tupleNo = 0;
ArrayList<String> tableTuples = tableToApplySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
int index = tableToApplySelectionOn.columnIndexMap.get(leftVal);
String[] rightArray = null;
int rightIndex = 0;

if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightIndex = tableToApplySelectionOn.columnIndexMap.get(rightVal);
rightArray = array;
} else {
rightArray = new String[1];
rightArray[0] = rightVal;
}

if (type.equalsIgnoreCase("string") || type.equalsIgnoreCase("char") || type.equalsIgnoreCase("varchar")) {
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
if (array[index].equals(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}
} else {
if (array[index].equals(rightArray[rightIndex].substring(1,rightArray[rightIndex].length() - 1))) {
listOfIndices.add(tupleNo);
}
}
} else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")) {
if (Double.parseDouble(array[index]) == Double.parseDouble(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}

} else if (type.equalsIgnoreCase("date")) {
String leftDate[] = array[index].split("-");
String rightDate[] = null;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal.toLowerCase())) {
rightDate = rightArray[rightIndex].split("-");
}
else{
rightDate = rightArray[rightIndex].substring(6, rightArray[rightIndex].length() - 2).split("-");
}

if (Integer.parseInt(leftDate[0]) == Integer.parseInt(rightDate[0]) && Integer.parseInt(leftDate[1]) == Integer.parseInt(rightDate[1]) && Integer.parseInt(leftDate[2]) == Integer.parseInt(rightDate[2])) {
listOfIndices.add(tupleNo);
}
}
}
}
} else if (expression instanceof NotEqualsTo) {

// this extracts the equal to clause in the WHERE clause
NotEqualsTo equalsExpression = (NotEqualsTo) expression;
// this extracts the left and the right expressions in the equal to clause
Expression leftExpression = ((Expression) equalsExpression.getLeftExpression());
Expression rightExpression = ((Expression) equalsExpression.getRightExpression());

if (leftExpression instanceof BinaryExpression || rightExpression instanceof BinaryExpression) {
listOfIndices = alegbricExpressionEvaluator(equalsExpression, tableToApplySelectionOn);

} else {
String leftVal = leftExpression.toString();
String rightVal = rightExpression.toString();

String type = tableToApplySelectionOn.columnDescriptionList.get(tableToApplySelectionOn.columnIndexMap.get(leftVal)).getColDataType().getDataType();
//String tuple = null;
int tupleNo = 0;
ArrayList<String> tableTuples = tableToApplySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
int index = tableToApplySelectionOn.columnIndexMap.get(leftVal);
String[] rightArray = null;
int rightIndex = 0;

if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightIndex = tableToApplySelectionOn.columnIndexMap.get(rightVal);
rightArray = array;
} else {
rightArray = new String[1];
rightArray[0] = rightVal;
}

if (type.equalsIgnoreCase("string") || type.equalsIgnoreCase("char") || type.equalsIgnoreCase("varchar")) {
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
if (!array[index].equals(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}
} else {
if (!array[index].equals(rightArray[rightIndex].substring(1,rightArray[rightIndex].length() - 1))) {
listOfIndices.add(tupleNo);
}
}
} else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")) {
if (Double.parseDouble(array[index]) != Double.parseDouble(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}

} else if (type.equalsIgnoreCase("date")) {
String leftDate[] = array[index].split("-");
String rightDate[] = null;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightDate = rightArray[rightIndex].split("-");
}
else{
rightDate = rightArray[rightIndex].substring(6, rightArray[rightIndex].length() - 2).split("-");
}

if (Integer.parseInt(leftDate[0]) != Integer.parseInt(rightDate[0]) && Integer.parseInt(leftDate[1]) == Integer.parseInt(rightDate[1]) && Integer.parseInt(leftDate[2]) != Integer.parseInt(rightDate[2])) {
listOfIndices.add(tupleNo);
}
}
}
}
}

else if (expression instanceof GreaterThanEquals) {
GreaterThanEquals greaterThanEqualsExpression = (GreaterThanEquals) expression;
Expression leftExpression = ((Expression) greaterThanEqualsExpression.getLeftExpression());
Expression rightExpression = ((Expression) greaterThanEqualsExpression.getRightExpression());

if (leftExpression instanceof BinaryExpression || rightExpression instanceof BinaryExpression) {
listOfIndices = alegbricExpressionEvaluator(greaterThanEqualsExpression, tableToApplySelectionOn);
} else {
String leftVal = leftExpression.toString().toLowerCase();
String rightVal = rightExpression.toString().toLowerCase();
String type = tableToApplySelectionOn.columnDescriptionList.get(tableToApplySelectionOn.columnIndexMap.get(leftVal)).getColDataType().getDataType();

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToApplySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
int index = tableToApplySelectionOn.columnIndexMap.get(leftVal);
String[] rightArray = null;
int rightIndex = 0;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightIndex = tableToApplySelectionOn.columnIndexMap.get(rightVal);
rightArray = array;
} else {
rightArray = new String[1];
rightArray[0] = rightVal;
}

if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")) {

if (Double.parseDouble(array[index]) >= Double.parseDouble(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}
} else if (type.equalsIgnoreCase("date")) {

String leftDate[] = array[index].split("-");
String rightDate[] = null;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightDate = rightArray[rightIndex].split("-");
}
else{
rightDate = rightArray[rightIndex].substring(6, rightArray[rightIndex].length() - 2).split("-");
}

if (Integer.parseInt(leftDate[0]) < Integer.parseInt(rightDate[0])) {

} else if (Integer.parseInt(leftDate[0]) == Integer.parseInt(rightDate[0])) {
if (Integer.parseInt(leftDate[1]) < Integer.parseInt(rightDate[1])) {
} else if (Integer.parseInt(leftDate[1]) == Integer.parseInt(rightDate[1])) {
if (Integer.parseInt(leftDate[2]) < Integer.parseInt(rightDate[2])) {
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
}
}
/*if(tableToApplySelectionOn.tableName.equalsIgnoreCase("lineitem") && expression.toString().equals("lineitem.receiptdate >= date('1994-01-01')")){
//System.out.println(listOfIndices);
}*/
}
} else if (expression instanceof GreaterThan) {
GreaterThan greaterThanExpression = (GreaterThan) expression;
Expression leftExpression = ((Expression) greaterThanExpression.getLeftExpression());
Expression rightExpression = ((Expression) greaterThanExpression.getRightExpression());

if (leftExpression instanceof BinaryExpression || rightExpression instanceof BinaryExpression) {
listOfIndices = alegbricExpressionEvaluator(greaterThanExpression, tableToApplySelectionOn);
} else {
String leftVal = leftExpression.toString();
String rightVal = rightExpression.toString();
String type = tableToApplySelectionOn.columnDescriptionList.get(tableToApplySelectionOn.columnIndexMap.get(leftVal)).getColDataType().getDataType();

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToApplySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
int index = tableToApplySelectionOn.columnIndexMap.get(leftVal);
String[] rightArray = null;
int rightIndex = 0;

if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightIndex = tableToApplySelectionOn.columnIndexMap.get(rightVal);
rightArray = array;
} else {
rightArray = new String[1];
rightArray[0] = rightVal;
}

if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")) {

if (Double.parseDouble(array[index]) > Double.parseDouble(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}

} else if (type.equalsIgnoreCase("date")) {
String leftDate[] = array[index].split("-");
String rightDate[] = null;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightDate = rightArray[rightIndex].split("-");
}
else{
rightDate = rightArray[rightIndex].substring(6, rightArray[rightIndex].length() - 2).split("-");
}

if (Integer.parseInt(leftDate[0]) < Integer.parseInt(rightDate[0])) {

} else if (Integer.parseInt(leftDate[0]) == Integer.parseInt(rightDate[0])) {
if (Integer.parseInt(leftDate[1]) < Integer.parseInt(rightDate[1])) {
} else if (Integer.parseInt(leftDate[1]) == Integer.parseInt(rightDate[1])) {
if (Integer.parseInt(leftDate[2]) <= Integer.parseInt(rightDate[2])) {
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
}

}
}

}

else if (expression instanceof MinorThan) {
MinorThan minorThanExpression = (MinorThan) expression;

Expression leftExpression = ((Expression) minorThanExpression.getLeftExpression());
Expression rightExpression = ((Expression) minorThanExpression.getRightExpression());

if (leftExpression instanceof BinaryExpression|| rightExpression instanceof BinaryExpression) {
listOfIndices = alegbricExpressionEvaluator(minorThanExpression, tableToApplySelectionOn);
} else {
String leftVal = leftExpression.toString();
String rightVal = rightExpression.toString();
String type = tableToApplySelectionOn.columnDescriptionList.get(tableToApplySelectionOn.columnIndexMap.get(leftVal.toLowerCase())).getColDataType().getDataType();

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToApplySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
int index = tableToApplySelectionOn.columnIndexMap.get(leftVal.toLowerCase());
String[] rightArray = null;
int rightIndex = 0;

if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightIndex = tableToApplySelectionOn.columnIndexMap.get(rightVal);
rightArray = array;
} else {
rightArray = new String[1];
rightArray[0] = rightVal;
}

if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")) {

if (Double.parseDouble(array[index]) < Double.parseDouble(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}
} else if (type.equalsIgnoreCase("date")) {
String leftDate[] = array[index].split("-");
String rightDate[] = null;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightDate = rightArray[rightIndex].split("-");
}
else{
rightDate = rightArray[rightIndex].substring(6, rightArray[rightIndex].length() - 2).split("-");
}
if (Integer.parseInt(leftDate[0]) > Integer.parseInt(rightDate[0])) {

} else if (Integer.parseInt(leftDate[0]) == Integer.parseInt(rightDate[0])) {
if (Integer.parseInt(leftDate[1]) > Integer.parseInt(rightDate[1])) {
} else if (Integer.parseInt(leftDate[1]) == Integer.parseInt(rightDate[1])) {
if (Integer.parseInt(leftDate[2]) >= Integer.parseInt(rightDate[2])) {
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
}

}
/*if(tableToApplySelectionOn.tableName.equalsIgnoreCase("lineitem") && expression.toString().equals("lineitem.commitdate < lineitem.receiptdate")){
System.out.println(listOfIndices);
}*/
}
}
else if (expression instanceof MinorThanEquals) {
MinorThanEquals minorEqualsThan = (MinorThanEquals) expression;
Expression leftExpression = ((Expression) minorEqualsThan.getLeftExpression());
Expression rightExpression = ((Expression) minorEqualsThan.getRightExpression());

if (leftExpression instanceof BinaryExpression || rightExpression instanceof BinaryExpression) {
listOfIndices = alegbricExpressionEvaluator(minorEqualsThan,tableToApplySelectionOn);
} else {
String leftVal = leftExpression.toString().toLowerCase();
String rightVal = rightExpression.toString().toLowerCase();
String type = tableToApplySelectionOn.columnDescriptionList.get(tableToApplySelectionOn.columnIndexMap.get(leftVal)).getColDataType().getDataType();

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToApplySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
int index = tableToApplySelectionOn.columnIndexMap.get(leftVal);

String[] rightArray = null;
int rightIndex = 0;

if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightIndex = tableToApplySelectionOn.columnIndexMap.get(rightVal);
rightArray = array;
} else {
rightArray = new String[1];
rightArray[0] = rightVal;
}

if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("decimal")) {

if (Double.parseDouble(array[index]) <= Double.parseDouble(rightArray[rightIndex])) {
listOfIndices.add(tupleNo);
}
} else if (type.equalsIgnoreCase("date")) {
String leftDate[] = array[index].split("-");
String rightDate[] = null;
if (tableToApplySelectionOn.columnIndexMap.containsKey(rightVal)) {
rightDate = rightArray[rightIndex].split("-");
}
else{
rightDate = rightArray[rightIndex].substring(6, rightArray[rightIndex].length() - 2).split("-");
}

if (Integer.parseInt(leftDate[0]) > Integer.parseInt(rightDate[0])) {

} else if (Integer.parseInt(leftDate[0]) == Integer.parseInt(rightDate[0])) {
if (Integer.parseInt(leftDate[1]) > Integer.parseInt(rightDate[1])) {
} else if (Integer.parseInt(leftDate[1]) == Integer.parseInt(rightDate[1])) {
if (Integer.parseInt(leftDate[2]) > Integer.parseInt(rightDate[2])) {
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
} else {
listOfIndices.add(tupleNo);
}
}

}

}
} else if (expression instanceof AndExpression) {
AndExpression andExpression = (AndExpression) expression;
Expression leftVal = ((Expression) andExpression.getLeftExpression());
Expression rightVal = ((Expression) andExpression.getRightExpression());

ArrayList<Integer> leftArr = expressionEvaluator(leftVal, tableToApplySelectionOn);
ArrayList<Integer> rightArr = expressionEvaluator(rightVal, tableToApplySelectionOn);

ArrayList<Integer> set = new ArrayList<Integer>();
for (int i : leftArr) {
if (rightArr.contains(i)) {
set.add(i);
}
}
listOfIndices = set;
}

else if (expression instanceof OrExpression) {
OrExpression orExpression = (OrExpression) expression;
Expression leftVal = ((Expression) orExpression.getLeftExpression());
Expression rightVal = ((Expression) orExpression.getRightExpression());

ArrayList<Integer> leftArr = expressionEvaluator(leftVal, tableToApplySelectionOn);
ArrayList<Integer> rightArr = expressionEvaluator(rightVal, tableToApplySelectionOn);
TreeSet<Integer> set = new TreeSet<Integer>();
for (int i : leftArr) {
set.add(i);
}
for (int i : rightArr) {
set.add(i);
}
for (int i : set) {
listOfIndices.add(i);
}
}
else if (expression instanceof Parenthesis){
ArrayList<Integer> expArr = expressionEvaluator(((Parenthesis)expression).getExpression(), tableToApplySelectionOn);
for(int i:expArr){
listOfIndices.add(i);
}
}

return listOfIndices;
}


private static ArrayList<Integer> alegbricExpressionEvaluator(Expression expression, Table tableToAppySelectionOn) throws IOException, ParseException {

ArrayList<Integer> listOfIndices = new ArrayList<Integer>();
String leftExp[];
String rightExp[];

if (expression instanceof GreaterThanEquals) {
String exps[] = expression.toString().split(">=");
ArrayList<String> left = braceExp(exps[0]);
ArrayList<String> right = braceExp(exps[1]);
String[] leftArr = new String[left.size()];
String[] rightArr = new String[right.size()];
leftArr = left.toArray(leftArr);
rightArr = right.toArray(rightArr);

leftExp = convertToPos(leftArr);
rightExp = convertToPos(rightArr);
//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
String evalLeftStack[] = new String[leftExp.length];
String evalRightStack[] = new String[rightExp.length];

for (int j = 0; j < leftExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(leftExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(leftExp[j]);
evalLeftStack[j] = array[index];
} else {
evalLeftStack[j] = leftExp[j];
}
}

for (int j = 0; j < rightExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(rightExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(rightExp[j]);
evalRightStack[j] = array[index];
} else {
evalRightStack[j] = rightExp[j];
}
}

if (evaluate(evalLeftStack) >= evaluate(evalRightStack)) {
listOfIndices.add(tupleNo);
}
}

} else if (expression instanceof GreaterThan) {
String exps[] = expression.toString().split(">");
ArrayList<String> left = braceExp(exps[0]);
ArrayList<String> right = braceExp(exps[1]);
String[] leftArr = new String[left.size()];
String[] rightArr = new String[right.size()];
leftArr = left.toArray(leftArr);
rightArr = right.toArray(rightArr);

leftExp = convertToPos(leftArr);
rightExp = convertToPos(rightArr);

String evalLeftStack[] = new String[leftExp.length];
String evalRightStack[] = new String[rightExp.length];

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;

String array[] = tuple.split("\\|");

for (int j = 0; j < leftExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(leftExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(leftExp[j]);
evalLeftStack[j] = array[index];
} else {
evalLeftStack[j] = leftExp[j];
}

}
for (int j = 0; j < rightExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(rightExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(rightExp[j]);
evalRightStack[j] = array[index];
} else {
evalRightStack[j] = rightExp[j];
}
}

if (evaluate(evalLeftStack) > evaluate(evalRightStack)) {
listOfIndices.add(tupleNo);
}

}

} else if (expression instanceof MinorThanEquals) {
String exps[] = expression.toString().split("<=");
ArrayList<String> left = braceExp(exps[0]);
ArrayList<String> right = braceExp(exps[1]);
String[] leftArr = new String[left.size()];
String[] rightArr = new String[right.size()];
leftArr = left.toArray(leftArr);
rightArr = right.toArray(rightArr);

leftExp = convertToPos(leftArr);
rightExp = convertToPos(rightArr);

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;

String array[] = tuple.split("\\|");
String evalLeftStack[] = new String[leftExp.length];
String evalRightStack[] = new String[rightExp.length];

for (int j = 0; j < leftExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(leftExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(leftExp[j]);
evalLeftStack[j] = array[index];
} else {
evalLeftStack[j] = leftExp[j];
}

}
for (int j = 0; j < rightExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(rightExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(rightExp[j]);
evalRightStack[j] = array[index];
} else {
evalRightStack[j] = rightExp[j];
}
}

if (evaluate(evalLeftStack) <= evaluate(evalRightStack)) {
listOfIndices.add(tupleNo);
}

}
}

else if (expression instanceof MinorThan) {
String exps[] = expression.toString().split("<");
ArrayList<String> left = braceExp(exps[0]);
ArrayList<String> right = braceExp(exps[1]);
String[] leftArr = new String[left.size()];
String[] rightArr = new String[right.size()];
leftArr = left.toArray(leftArr);
rightArr = right.toArray(rightArr);
leftExp = convertToPos(leftArr);
rightExp = convertToPos(rightArr);

//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
String evalLeftStack[] = new String[leftExp.length];
String evalRightStack[] = new String[rightExp.length];

for (int j = 0; j < leftExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(leftExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(leftExp[j]);
evalLeftStack[j] = array[index];
} else {
evalLeftStack[j] = leftExp[j];
}
}
for (int j = 0; j < rightExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(rightExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(rightExp[j]);
evalRightStack[j] = array[index];
} else {
evalRightStack[j] = rightExp[j];
}
}

if (evaluate(evalLeftStack) < evaluate(evalRightStack)) {
listOfIndices.add(tupleNo);
}
}
}

else if (expression instanceof EqualsTo) {
String exps[] = expression.toString().split("=");
ArrayList<String> left = braceExp(exps[0]);
ArrayList<String> right = braceExp(exps[1]);
String[] leftArr = new String[left.size()];
String[] rightArr = new String[right.size()];
leftArr = left.toArray(leftArr);
rightArr = right.toArray(rightArr);
leftExp = convertToPos(leftArr);
rightExp = convertToPos(rightArr);
//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
String evalLeftStack[] = new String[leftExp.length];
String evalRightStack[] = new String[rightExp.length];

for (int j = 0; j < leftExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(leftExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(leftExp[j]);
evalLeftStack[j] = array[index];
} else {
evalLeftStack[j] = leftExp[j];
}
}
for (int j = 0; j < rightExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(rightExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(rightExp[j]);
evalRightStack[j] = array[index];
} else {
evalRightStack[j] = rightExp[j];
}
}

if (evaluate(evalLeftStack) == evaluate(evalRightStack)) {
listOfIndices.add(tupleNo);
}
}
} else if(expression instanceof NotEqualsTo){

String exps[] = expression.toString().split("<>");
ArrayList<String> left = braceExp(exps[0]);
ArrayList<String> right = braceExp(exps[1]);
String[] leftArr = new String[left.size()];
String[] rightArr = new String[right.size()];
leftArr = left.toArray(leftArr);
rightArr = right.toArray(rightArr);
leftExp = convertToPos(leftArr);
rightExp = convertToPos(rightArr);
//String tuple = null;
int tupleNo = 0;

ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){
tupleNo++;
String array[] = tuple.split("\\|");
String evalLeftStack[] = new String[leftExp.length];
String evalRightStack[] = new String[rightExp.length];

for (int j = 0; j < leftExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(leftExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(leftExp[j]);
evalLeftStack[j] = array[index];
} else {
evalLeftStack[j] = leftExp[j];
}
}
for (int j = 0; j < rightExp.length; j++) {
if (tableToAppySelectionOn.columnIndexMap.containsKey(rightExp[j])) {
int index = tableToAppySelectionOn.columnIndexMap.get(rightExp[j]);
evalRightStack[j] = array[index];
} else {
evalRightStack[j] = rightExp[j];
}
}

if (evaluate(evalLeftStack) != evaluate(evalRightStack)) {
listOfIndices.add(tupleNo);
}
}
}

return listOfIndices;
}

public static ArrayList<String> braceExp(String str) throws ParseException {

CCJSqlParser parser = new CCJSqlParser(new StringReader(str));
Expression exp = parser.SimpleExpression();
ArrayList<String> strArrList = new ArrayList<String>();
if (exp instanceof Parenthesis) {
Expression binaryExp = ((Parenthesis) exp).getExpression();
strArrList.add("(");
if (binaryExp instanceof BinaryExpression) {
for (String s : braceExp(binaryExp.toString())) {
strArrList.add(s);
}
}
strArrList.add(")");

} else if (exp instanceof Addition) {
Expression leftExpression = ((Addition) exp).getLeftExpression();
Expression rightExpression = ((Addition) exp).getRightExpression();
ArrayList<String> leftExp = new ArrayList<String>();
ArrayList<String> rightExp = new ArrayList<String>();

if (leftExpression instanceof Parenthesis || leftExpression instanceof BinaryExpression) {
leftExp = braceExp(leftExpression.toString());
} else {
leftExp.add(leftExpression.toString());
}
rightExp.add("+");
if (rightExpression instanceof Parenthesis || rightExpression instanceof BinaryExpression) {
for (String s : braceExp(rightExpression.toString())) {
rightExp.add(s);
}
} else {
rightExp.add(rightExpression.toString());
}
for (String s : leftExp) {
strArrList.add(s);
}
for (String s : rightExp) {
strArrList.add(s);
}
} else if (exp instanceof Multiplication) {
Expression leftExpression = ((Multiplication) exp).getLeftExpression();
Expression rightExpression = ((Multiplication) exp).getRightExpression();
ArrayList<String> leftExp = new ArrayList<String>();
ArrayList<String> rightExp = new ArrayList<String>();

if (leftExpression instanceof Parenthesis || leftExpression instanceof BinaryExpression) {
leftExp = braceExp(leftExpression.toString());
} else {
leftExp.add(leftExpression.toString());
}
rightExp.add("*");
if (rightExpression instanceof Parenthesis || rightExpression instanceof BinaryExpression) {
for (String s : braceExp(rightExpression.toString())) {
rightExp.add(s);
}
} else {
rightExp.add(rightExpression.toString());
}
for (String s : leftExp) {
strArrList.add(s);
}
for (String s : rightExp) {
strArrList.add(s);
}

} else if (exp instanceof Subtraction) {
Expression leftExpression = ((Subtraction) exp).getLeftExpression();
Expression rightExpression = ((Subtraction) exp).getRightExpression();
ArrayList<String> leftExp = new ArrayList<String>();
ArrayList<String> rightExp = new ArrayList<String>();
if (leftExpression instanceof Parenthesis || leftExpression instanceof BinaryExpression) {
leftExp = braceExp(leftExpression.toString());
} else {
leftExp.add(leftExpression.toString());
}
rightExp.add("-");
if (rightExpression instanceof Parenthesis || rightExpression instanceof BinaryExpression) {
for (String s : braceExp(rightExpression.toString())) {
rightExp.add(s);
}
} else {
rightExp.add(rightExpression.toString());
}
for (String s : leftExp) {
strArrList.add(s);
}
for (String s : rightExp) {
strArrList.add(s);
}

} else if (exp instanceof Division) {
Expression leftExpression = ((Division) exp).getLeftExpression();
Expression rightExpression = ((Division) exp).getRightExpression();
ArrayList<String> leftExp = new ArrayList<String>();
ArrayList<String> rightExp = new ArrayList<String>();
if (leftExpression instanceof Parenthesis || leftExpression instanceof BinaryExpression) {
leftExp = braceExp(leftExpression.toString());
} else {
leftExp.add(leftExpression.toString());
}
rightExp.add("/");
if (rightExpression instanceof Parenthesis || rightExpression instanceof BinaryExpression) {
for (String s : braceExp(rightExpression.toString())) {
rightExp.add(s);
}
} else {
rightExp.add(rightExpression.toString());
}
for (String s : leftExp) {
strArrList.add(s);
}
for (String s : rightExp) {
strArrList.add(s);
}
} else {
strArrList.add(exp.toString());
}

return strArrList;

}

// this method is to convert infix expression to postfix expression
public static String[] convertToPos(String[] string) {

HashMap<String, Integer> operator = new HashMap<String, Integer>();
operator.put("*", 2);
operator.put("/", 2);
operator.put("+", 1);
operator.put("-", 1);
operator.put("(", 3);
operator.put(")", 0);

Stack<String> evaluationStack = new Stack<String>();
Stack<String> operatorStack = new Stack<String>();

for (int i = 0; i < string.length; i++) {
if (!(operator.containsKey(string[i]))) {
evaluationStack.push(string[i]);
} else {
int precedence = operator.get(string[i]);
if (!operatorStack.isEmpty()) {
if (operator.get(operatorStack.peek()) >= precedence) {
if (!(operatorStack.peek().equals("(") || operatorStack.peek().equals(")") || operatorStack.peek().equals("(")))
evaluationStack.push(operatorStack.pop());
}
}
operatorStack.push(string[i]);
}
}

while (!operatorStack.isEmpty()) {
if (!((operatorStack.peek().equals(")") || operatorStack.peek().equals("(")))) {
evaluationStack.push(operatorStack.pop());
} else {
operatorStack.pop();
}
}
String str[] = new String[evaluationStack.size()];

int c = str.length - 1;
while (!evaluationStack.isEmpty()) {
str[c] = evaluationStack.pop();
c--;
}

return str;

}

// this method is to evaluate the postfix expression
public static double evaluate(String arr[]) {
HashMap<String, Integer> operators = new HashMap<String, Integer>();
operators.put("*", 1);
operators.put("/", 2);
operators.put("+", 3);
operators.put("-", 4);

Stack<String> evaluationStack = new Stack<String>();

for (String s : arr) {
if (!operators.containsKey(s)) {
evaluationStack.add(s);
} else {
double val1 = Double.parseDouble(evaluationStack.pop());
double val2 = Double.parseDouble(evaluationStack.pop());
double result = 0.0;
if (operators.get(s) == 1) {
result = val1 * val2;
} else if (operators.get(s) == 2) {
result = val2 / val1;
} else if (operators.get(s) == 3) {
result = val1 + val2;
} else if (operators.get(s) == 4) {
result = val2 - val1;
}
evaluationStack.push(Double.toString(result));
}
}

return Double.parseDouble(evaluationStack.firstElement());
}

// this method populates the new table
private static void populateTable(Table resultantTable,
Table tableToAppySelectionOn, ArrayList<Integer> listOfIndices)
throws IOException {

// now write to the .dat file describing the Where clause
FileWriter fwr = new FileWriter(resultantTable.tableFilePath, true);
BufferedWriter bwr = new BufferedWriter(fwr);

//String tuple;
int tupleNo = 0;
// this string builder object is used to speed up the processing of writing in files
StringBuilder sb = new StringBuilder("");
int count = 0;
ArrayList<String> tableTuples = tableToAppySelectionOn.tableTuples;
//while ((tuple = tableToApplySelectionOn.returnTuple()) != null) {
for(String tuple:tableTuples){	
tupleNo++;
// this is the string that I need to write down
if (listOfIndices.contains(tupleNo)) {
resultantTable.tableTuples.add(tuple);
/*bwr.write(tuple);
bwr.write("\n");
bwr.flush();*/
++count;
// this is the condition when we write in the file, this just corresponds to one I/O
/*if(count == 100000000){
sb.append(tuple + "\n");
bwr.write(sb.toString());
count = 0;
sb = new StringBuilder("");
}else{
sb.append(tuple + "\n");
}*/
}
}
// if the above condition is not satisfied, then just write the string builder to the file
count = 0;
/*bwr.write(sb.toString());
sb = null;
bwr.close();*/
}


public static String extractJoinCond(String table1, String table2, Expression expression) {
ArrayList<String> arrayList = new ArrayList<String>();
HashSet<String> set = extractCond(expression);

for (String s : set) {
if (s.contains(" = ")) {
String strArr[] = s.split("=");
for (int i = 0; i < strArr.length; i++) {
strArr[i] = strArr[i].trim();
}
if (strArr[0].contains(".") && strArr[1].contains(".")) {
if ((strArr[0].split("\\.")[0].equalsIgnoreCase(table1) || strArr[0].split("\\.")[0].equalsIgnoreCase(table2)) &&
(strArr[1].split("\\.")[0].equalsIgnoreCase(table1) || strArr[1].split("\\.")[0].equalsIgnoreCase(table2)))
{
return strArr[0].split("\\.")[1];
}
}
}
}
return null;
}

public static HashSet<Expression> extractNonJoinExp(Expression expression){
HashSet<Expression> allExp = extractAllExp(expression);
HashSet<Expression> nonJoinExp = new HashSet<Expression>();
HashSet<String> tablesToJoin = new HashSet<String>();
tablesToJoin.add("customer");
tablesToJoin.add("nation");
tablesToJoin.add("orders");
tablesToJoin.add("lineitem");
tablesToJoin.add("region");
tablesToJoin.add("partsupp");
tablesToJoin.add("part");
tablesToJoin.add("supplier");
tablesToJoin.add("n1");
tablesToJoin.add("n2");
tablesToJoin.add("r1");
tablesToJoin.add("p1");
tablesToJoin.add("ps1");
tablesToJoin.add("s1");
tablesToJoin.add("CUSTOMER");
tablesToJoin.add("NATION");
tablesToJoin.add("ORDERS");
tablesToJoin.add("LINEITEM");
tablesToJoin.add("REGION");
tablesToJoin.add("PARTSUPP");
tablesToJoin.add("PART");
tablesToJoin.add("SUPPLIER");
tablesToJoin.add("n1");
tablesToJoin.add("n2");
tablesToJoin.add("r1");
tablesToJoin.add("p1");
tablesToJoin.add("ps1");
tablesToJoin.add("s1");
for(Expression exp:allExp){
if (!(exp instanceof EqualsTo)){
nonJoinExp.add(exp);
}
else{
String leftVal = ((EqualsTo)exp).getLeftExpression().toString();
String rightVal = ((EqualsTo)exp).getRightExpression().toString();
String leftTable = null;
String rightTable = null;
if(leftVal.contains(".")){
leftTable = leftVal.split("\\.")[0];
}
if(rightVal.contains(".")){
rightTable = rightVal.split("\\.")[0];
}
if(!(tablesToJoin.contains(leftTable) && tablesToJoin.contains(rightTable))){
nonJoinExp.add(exp);
}
}
}
return nonJoinExp;
}
public static HashSet<Expression> extractAllExp(Expression expression){
HashSet<Expression> hashSet = new HashSet<Expression>();
Expression leftVal = null;
Expression rightVal = null;

if (expression instanceof AndExpression) {
AndExpression mte = (AndExpression) expression;
leftVal = ((Expression) mte.getLeftExpression());
rightVal = ((Expression) mte.getRightExpression());

if (leftVal instanceof AndExpression || leftVal instanceof OrExpression) {
HashSet<Expression> array = extractAllExp(leftVal);
for (Expression s : array) {
hashSet.add(s);
}
hashSet.add(rightVal);
} else {
//if (leftVal instanceof EqualsTo) {
hashSet.add(leftVal);
//}
//if (rightVal instanceof EqualsTo) {
hashSet.add(rightVal);
//}
}

} else if (expression instanceof OrExpression) {
OrExpression mte = (OrExpression) expression;
leftVal = ((Expression) mte.getLeftExpression());
rightVal = ((Expression) mte.getRightExpression());;

if (leftVal instanceof AndExpression || leftVal instanceof OrExpression) {
HashSet<Expression> array = extractAllExp(leftVal);
for (Expression s : array) {
hashSet.add(s);
}
hashSet.add(rightVal);
} else {
//if (leftVal instanceof EqualsTo) {
hashSet.add(leftVal);
//}
//if (rightVal instanceof EqualsTo) {
hashSet.add(rightVal);
//}
}

}
else {
hashSet.add(expression);
}
return hashSet;
}
public static HashSet<String> extractCond(Expression expression) {
//	System.out.println(expression.toString()+"\n\n");
HashSet<String> hashSet = new HashSet<String>();
HashSet<Expression> hashExpSet = extractAllExp(expression);
for(Expression exp:hashExpSet){
hashSet.add(exp.toString());
}
/*Expression leftVal = null;
Expression rightVal = null;

if (expression instanceof AndExpression) {
AndExpression mte = (AndExpression) expression;
leftVal = ((Expression) mte.getLeftExpression());
rightVal = ((Expression) mte.getRightExpression());

if (leftVal instanceof AndExpression || leftVal instanceof OrExpression) {
HashSet<String> array = extractCond(leftVal);
for (String s : array) {
arrayList.add(s);
}
arrayList.add(rightVal.toString());
} else {
//if (leftVal instanceof EqualsTo) {
arrayList.add(leftVal.toString());
//}
//if (rightVal instanceof EqualsTo) {
arrayList.add(rightVal.toString());
//}
}

} else if (expression instanceof OrExpression) {
OrExpression mte = (OrExpression) expression;
leftVal = ((Expression) mte.getLeftExpression());
rightVal = ((Expression) mte.getRightExpression());;

if (leftVal instanceof AndExpression || leftVal instanceof OrExpression) {
HashSet<String> array = extractCond(leftVal);
for (String s : array) {
arrayList.add(s);
}
arrayList.add(rightVal.toString());
} else {
//if (leftVal instanceof EqualsTo) {
arrayList.add(leftVal.toString());
//}
//if (rightVal instanceof EqualsTo) {
arrayList.add(rightVal.toString());
//}
}

}
else {
arrayList.add(expression.toString());
}*/
return hashSet;
}
public static ArrayList<String> evaluateJoinCondition (Table table1,Table table2,Expression expression)
{
/*System.out.println("table 1 name:" + table1.tableName);
System.out.println("table 2 name:" + table2.tableName);*/
ArrayList<String> arrayList = new ArrayList<String>();

HashSet<String> set = extractCond(expression);
for(String s: set)
{
String[] strArr=null;
if(s.contains("="))
{
strArr=s.split("=");
for (int i = 0; i < strArr.length; i++) {
strArr[i] = strArr[i].trim();
}
if(!table1.tableName.contains("|"))
{

if ((strArr[0].split("\\.")[0].equalsIgnoreCase(table1.tableName) || strArr[0].split("\\.")[0].equalsIgnoreCase(table2.tableName)) &&
(strArr[1].split("\\.")[0].equalsIgnoreCase(table1.tableName) || strArr[1].split("\\.")[0].equalsIgnoreCase(table2.tableName))){

String FirsttableName=table1.tableName;
String secondtableName=table2.tableName;
String attr=strArr[0].substring(strArr[0].indexOf(".")+1,strArr[0].length());
arrayList.add(FirsttableName);
arrayList.add(secondtableName);
arrayList.add(attr);
return arrayList;

}


}
else
{
String[] newTableName=table1.tableName.trim().split("\\|");
for(int i=0;i<newTableName.length;i++)
{
if((strArr[0].split("\\.")[0].equalsIgnoreCase(newTableName[i])&&strArr[1].split("\\.")[0].equalsIgnoreCase(table2.tableName))||
(strArr[1].split("\\.")[0].equalsIgnoreCase(newTableName[i])&&strArr[0].split("\\.")[0].equalsIgnoreCase(table2.tableName)))
{
String FirsttableName=newTableName[i];
String secondtableName=table2.tableName;
String attr=strArr[0].substring(strArr[0].indexOf(".")+1,strArr[0].length());
arrayList.add(FirsttableName);
arrayList.add(secondtableName);
arrayList.add(attr);
return arrayList;

}

}
}
}


}

return arrayList;
}
}
