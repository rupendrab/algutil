package com.alg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.alg.datastructures.MinHeap;

public class Hungarian
{
    int[][] matrix;
    Pattern blankLine = Pattern.compile("^\\s*$");
    Pattern matrixLine = Pattern.compile("^\\s*([0-9]+\\s+)*([0-9]+\\s*)$");
    int largestNo = Integer.MIN_VALUE;
    MinHeap<ZeroPositions> rowZero = new MinHeap<>(new ZeroPositions[] {});
    MinHeap<ZeroPositions> colZero = new MinHeap<>(new ZeroPositions[] {});
    boolean DEBUG = false;

    public Hungarian(int[][] matrix)
    {
        super();
        this.matrix = matrix;
    }
    
    public Hungarian(String fileName, int no) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int atNO = 0;
        int columns = 0;
        boolean inMatrix = false;
        ArrayList<int[]> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null)
        {
            if (blankLine.matcher(line).find())
            {
                if (inMatrix)
                {
                    inMatrix = false;
                    if (atNO == no)
                    {
                        break;
                    }
                }
            }
            else if (matrixLine.matcher(line).find())
            {
                if (! inMatrix)
                {
                    inMatrix = true;
                    atNO += 1;
                }
                if (atNO == no)
                {
                    String[] fields = line.trim().split("\\s+");
                    int[] matrixLine = new int[fields.length];
                    for (int i=0; i<fields.length; i++)
                    {
                        matrixLine[i] = Integer.parseInt(fields[i]);
                        if (matrixLine[i] > largestNo)
                        {
                            largestNo = matrixLine[i];
                        }
                    }
                    lines.add(matrixLine);
                    columns = fields.length;
                }
            }
        }
        matrix = new int[lines.size()][columns];
        lines.toArray(matrix);
        reader.close();
    }
    
    public int[][] copy(int[][] somematrix)
    {
        if (somematrix == null || somematrix.length == 0 || somematrix[0] == null || somematrix[0].length == 0)
        {
            return null;
        }
        int rows = somematrix.length;
        int columns = somematrix[0].length;
        int[][] copied = new int[rows][columns];
        for (int row=0; row<rows; row++)
        {
            for (int column=0; column<columns; column++)
            {
                copied[row][column] = somematrix[row][column];
            }
        }
        
        return copied;
    }
    
    public int rowmin(int[][] matrix, int row)
    {
        if (matrix == null || row > matrix.length - 1)
        {
            return 0;
        }
        int minValue = Integer.MAX_VALUE;
        int columns = matrix[row].length;
        for (int column=0; column<columns; column++)
        {
            if (matrix[row][column] < minValue)
            {
                minValue = matrix[row][column];
            }
        }
        return minValue;
    }
    
    public int colmin(int[][] matrix, int col)
    {
        if (matrix == null || matrix.length == 0 || col > matrix[0].length - 1)
        {
            return 0;
        }
        int minValue = Integer.MAX_VALUE;
        int rows = matrix.length;
        for (int row=0; row<rows; row++)
        {
            if (matrix[row][col] < minValue)
            {
                minValue = matrix[row][col];
            }
        }
        return minValue;
    }
    
    public int[][] subtractRowMins(int[][] matrix, Hashtable<Integer, HashSet<Integer>> zeros, boolean copy)
    {
        int[][] copied = matrix;
        if (copy)
        {
            copied = copy(matrix);
        }
        int rows = copied.length;
        int columns = copied[0].length;
        for (int row=0; row<rows; row++)
        {
            int rowmin = rowmin(copied, row);
            for (int column=0; column<columns; column++)
            {
                copied[row][column] -= rowmin;
                if (copied[row][column] == 0)
                {
                    addZero(zeros, row, column);
                }
            }
        }
        return copied;
    }
    
    public int[][] subtractColMins(int[][] matrix, Hashtable<Integer, HashSet<Integer>> zeros, boolean copy)
    {
        int[][] copied = matrix;
        if (copy)
        {
            copied = copy(matrix);
        }
        int rows = copied.length;
        int columns = copied[0].length;
        for (int column=0; column<columns; column++)
        {
            int colmin = colmin(copied, column);
            for (int row=0; row<rows; row++)
            {
                copied[row][column] -= colmin;
                if (copied[row][column] == 0)
                {
                    addZero(zeros, row, column);
                }
            }
        }
        return copied;
    }
    
    public void print()
    {
        print(matrix);
    }
    
    public void print(int[][] matrix)
    {
        if (matrix == null)
        {
            return;
        }
        int rows = matrix.length;
        int columns = matrix[0].length;
        System.out.println(String.format("Rows = %d", rows));
        System.out.println(String.format("Colums = %d", columns));
        System.out.println();
        int width = ("" + largestNo).length();
        String colFormat = "%" + width + "d";
        for (int row=0; row<rows; row++)
        {
            for (int column=0; column<columns; column++)
            {
                if (column > 0)
                {
                    System.out.print(" ");
                }
                System.out.print(String.format(colFormat, matrix[row][column]));
            }
            System.out.println();
        }
    }
    
    @Deprecated
    public void assignTaskOld(int[][] matrix, Hashtable<Integer, Integer> assignedRows, Hashtable<Integer, HashSet<Integer>> crossedOutZeros)
    {
        int rows = matrix.length;
        int columns = matrix[0].length;
        for (int row=0; row<rows; row++)
        {
            boolean rowIsAssigned = false;
            for (int column=0; column<columns; column++)
            {
                if (matrix[row][column] == 0)
                {
                    HashSet<Integer> crossed = crossedOutZeros.get(row);
                    if (crossed == null)
                    {
                        crossed = new HashSet<>();
                        crossedOutZeros.put(row, crossed);
                    }
                    if (rowIsAssigned)
                    {
                        crossed.add(column);
                        continue;
                    }
                    else if (crossed != null && crossed.contains(column))
                    {
                        continue; // This zero is crossed out, cannot be used
                    }
                    else
                    {
                        assignedRows.put(row, column);
                        rowIsAssigned = true;
                        // Cross out any zero in the same column of a subsequent row
                        for (int nextrow=row+1; nextrow<rows; nextrow++)
                        {
                            if (matrix[nextrow][column] == 0)
                            {
                                HashSet<Integer> crossedNext = crossedOutZeros.get(nextrow);
                                if (crossedNext == null)
                                {
                                    crossedNext = new HashSet<>();
                                    crossedOutZeros.put(nextrow, crossedNext);
                                }
                                crossedNext.add(column);
                            }
                        }
                    }
                    
                }
            }
        }
    }
    
    public void addToCrossedOutZeros(Hashtable<Integer, HashSet<Integer>> crossedOutZeros, int row, int column)
    {
        HashSet<Integer> crossedNext = crossedOutZeros.get(row);
        if (crossedNext == null)
        {
            crossedNext = new HashSet<>();
            crossedOutZeros.put(row, crossedNext);
        }
        crossedNext.add(column);
    }
    
    public void assignFromRow(ZeroPositions zp, HashSet<Integer> unassignableRows,
            Hashtable<Integer, Integer> assignedRows, 
            Hashtable<Integer, HashSet<Integer>> crossedOutZeros)
    {
        if (DEBUG)
        {
            System.out.println("Assign from row: " + zp);
        }
        if (zp.getSize() == 0)
        {
            unassignableRows.add(zp.getIndex());
            if (DEBUG)
            {
                System.out.println("Assigned Rows = " + assignedRows);
                rowZero.printData();
                colZero.printData();
            }
            return;
        }
        int row = zp.index;
        ArrayList<Integer> subIndices = new ArrayList<>(zp.getSubIndices());
        int i = 0;
        for (int column : subIndices)
        {
            i++;
            if (i == 1)
            {
                assignedRows.put(row, column);
                ZeroPositions colData = colZero.getActualValue(new ZeroPositions(column));
                if (colData != null)
                {
                    TreeSet<Integer> rowsToInvalidate = colData.getSubIndices();
                    rowsToInvalidate.remove(row);
                    ArrayList<Integer> rowsInv = new ArrayList<Integer>(rowsToInvalidate);
                    for (Integer otherrow : rowsInv)
                    {
                        if (DEBUG)
                        {
                            System.out.println(String.format("  Dropping %d, %d", otherrow, column));
                        }
                        addToCrossedOutZeros(crossedOutZeros, otherrow, column);
                        dropZeroFromColumnHeap(otherrow, column);
                        dropZeroFromRowHeap(otherrow, column);
                    }
                }
            }
            else
            {
                // Only process first entry, others should be crossed out
                addToCrossedOutZeros(crossedOutZeros, row, column);
                dropZeroFromColumnHeap(row, column);
                dropZeroFromRowHeap(row, column);
            }
        }
        // Print Overall Status after operation
        if (DEBUG)
        {
            System.out.println("Assigned Rows = " + assignedRows);
            rowZero.printData();
            colZero.printData();
        }
    }
    
    public void assignFromColumn(ZeroPositions zp, HashSet<Integer> unassignableColumns,
            Hashtable<Integer, Integer> assignedRows, 
            Hashtable<Integer, HashSet<Integer>> crossedOutZeros)
    {
        if (DEBUG)
        {
            System.out.println("Assign from column: " + zp);
        }
        if (zp.getSize() == 0)
        {
            unassignableColumns.add(zp.getIndex());
            if (DEBUG)
            {
                System.out.println("Assigned Rows = " + assignedRows);
                rowZero.printData();
                colZero.printData();
            }
            return;
        }
        int column = zp.index;
        ArrayList<Integer> subIndices = new ArrayList<>(zp.getSubIndices());
        int i = 0;
        for (int row : subIndices)
        {
            i++;
            if (i == 1)
            {
                if (assignedRows.contains(row))
                {
                    continue;
                }
                assignedRows.put(row, column);
                ZeroPositions rowData = rowZero.getActualValue(new ZeroPositions(row));
                if (rowData != null)
                {
                    TreeSet<Integer> colsToInvalidate = rowData.getSubIndices();
                    colsToInvalidate.remove(column);
                    ArrayList<Integer> colsInv = new ArrayList<Integer>(colsToInvalidate);
                    for (Integer othercolumn : colsInv)
                    {
                        addToCrossedOutZeros(crossedOutZeros, row, othercolumn);
                        dropZeroFromColumnHeap(row, othercolumn);
                        dropZeroFromRowHeap(row, othercolumn);
                    }
                }
            }
            else
            {
                // Only process first entry, others should be crossed out
                addToCrossedOutZeros(crossedOutZeros, row, column);
                dropZeroFromColumnHeap(row, column);
                dropZeroFromRowHeap(row, column);
            }
        }
        // Print Overall Status after operation
        if (DEBUG)
        {
            System.out.println("Assigned Rows = " + assignedRows);
            rowZero.printData();
            colZero.printData();
        }
    }
    
    public void assignTask(int[][] matrix, Hashtable<Integer, Integer> assignedRows, 
            Hashtable<Integer, HashSet<Integer>> crossedOutZeros,
            HashSet<Integer> unassignableRows, HashSet<Integer> unassignableColumns)
    {
        int rows = matrix.length;
        int columns = matrix[0].length;
        ZeroPositions zp = null;
        ZeroPositions zp2 = null;

        while (true)
        {
            if (assignedRows.size() + unassignableRows.size() == rows)
            {
                break;
            }
            if (rowZero.getSize() == 0 && colZero.getSize() == 0)
            {
                break;
            }
            if (DEBUG)
            {
                System.out.println(rowZero.getSize() + " *** " + colZero.getSize());
            }
            zp = rowZero.first();
            zp2 = colZero.first();
            if (zp != null && (zp2 == null || zp.getSize() <= zp2.getSize()))
            {
                zp = rowZero.takeFirst();
                assignFromRow(zp, unassignableRows, assignedRows, crossedOutZeros);
            }
            else if (zp2 != null && (zp == null || zp.getSize() > zp2.getSize()))
            {
                zp2 = colZero.takeFirst();
                assignFromColumn(zp2, unassignableColumns, assignedRows, crossedOutZeros);
            }
        }
    }
    
    public void addZeroToRowHeap(int row, int column)
    {
        ZeroPositions shadowRow = new ZeroPositions(row);
        ZeroPositions rowZeroData = rowZero.getActualValue(shadowRow);
        if (rowZeroData == null)
        {
            rowZeroData = shadowRow;
        }
        else
        {
            rowZero.deleteItem(rowZeroData);
        }
        rowZeroData.addZeroPosition(column);
        rowZero.insertItem(rowZeroData);
    }
    
    public void dropZeroFromRowHeap(int row, int column)
    {
        ZeroPositions shadowRow = new ZeroPositions(row);
        ZeroPositions rowZeroData = rowZero.getActualValue(shadowRow);
        if (rowZeroData == null)
        {
            return;
        }
        else
        {
            rowZero.deleteItem(rowZeroData);
        }
        rowZeroData.deleteZeroPosition(column);
        rowZero.insertItem(rowZeroData);
    }
    
    public void addZeroToColumnHeap(int row, int column)
    {
        ZeroPositions shadowColumn = new ZeroPositions(column);
        ZeroPositions columnZeroData = colZero.getActualValue(shadowColumn);
        if (columnZeroData == null)
        {
            columnZeroData = shadowColumn;
        }
        else
        {
            colZero.deleteItem(columnZeroData);
        }
        columnZeroData.addZeroPosition(row);
        colZero.insertItem(columnZeroData);
    }
    
    public void dropZeroFromColumnHeap(int row, int column)
    {
        ZeroPositions shadowColumn = new ZeroPositions(column);
        ZeroPositions columnZeroData = colZero.getActualValue(shadowColumn);
        if (columnZeroData == null)
        {
            return;
        }
        else
        {
            colZero.deleteItem(columnZeroData);
        }
        if (DEBUG)
        {
            System.out.println("  dropZeroFromColumnHeap, columnZeroOrig = " + columnZeroData);
        }
        columnZeroData.deleteZeroPosition(row);
        colZero.insertItem(columnZeroData);
        if (DEBUG)
        {
            System.out.println("  dropZeroFromColumnHeap, columnZeroNew = " + columnZeroData);
        }
    }
    
    public void addZero(Hashtable<Integer, HashSet<Integer>> zeros, int row, int column)
    {
        // System.out.println(String.format("Adding zero at %d, %d", row, column));
        HashSet<Integer> rowData = zeros.get(row);
        if (rowData == null)
        {
            rowData = new HashSet<>();
            zeros.put(row, rowData);
        }
        rowData.add(column);
        addZeroToRowHeap(row, column);
        addZeroToColumnHeap(row, column);
    }
    
    public void dropZero(Hashtable<Integer, HashSet<Integer>> zeros, int row, int column)
    {
        HashSet<Integer> rowData = zeros.get(row);
        if (rowData == null)
        {
            return;
        }
        rowData.remove(column);
        dropZeroFromRowHeap(row, column);
        dropZeroFromColumnHeap(row, column);
    }
    
    public boolean isZero(Hashtable<Integer, HashSet<Integer>> zeros, int row, int column)
    {
        HashSet<Integer> rowData = zeros.get(row);
        if (rowData == null)
        {
            return false;
        }
        return rowData.contains(column);
    }
    
    public boolean onlyZero(Hashtable<Integer, HashSet<Integer>> zeros, int row, int column)
    {
        HashSet<Integer> rowData = zeros.get(row);
        if (rowData == null)
        {
            return false;
        }
        return (rowData.size() == 1 && rowData.contains(column));
    }
    
    public int noInvalidations(Hashtable<Integer, HashSet<Integer>> zeros, int row, int column)
    {
        //TODO: Incomplete
        ArrayList<Integer> rows = new ArrayList<Integer>(zeros.keySet());
        Collections.sort(rows);
        return 0;
    }
    
    public void drawLines(int[][] working, Hashtable<Integer, Integer> assignedRows,
            TreeSet<Integer> unmarkedRows, TreeSet<Integer> markedColumns)
    {
        TreeSet<Integer> markedRows = new TreeSet<>();
        
        TreeSet<Integer> markedRowsNew = new TreeSet<>();
        TreeSet<Integer> markedColumnsNew = new TreeSet<>();

        // Step 1. Add lines for all unassigned rows
        for (int i=0; i<working.length; i++)
        {
            if (! assignedRows.containsKey(i))
            {
                markedRows.add(i);
                markedRowsNew.add(i);
            }
        }
        
        int counter = 0;
        while (true)
        {
            counter++;
            if (counter > 10)
            {
                System.out.println("Number of loops exceeded " + counter + ", exiting!!!");
                break;
            }
            // Step 2. For all new marked rows, add columns lines where there is a zero in the row
            
            if (DEBUG)
            {
                System.out.println("Newly marked rows = " + markedRowsNew);
            }
            markedColumnsNew = new TreeSet<>();
            for (int row : markedRowsNew)
            {
                for (int column=0; column<working[row].length; column++)
                {
                    if (working[row][column] == 0 && ! markedColumns.contains(column))
                    {
                        markedColumnsNew.add(column);
                        markedColumns.add(column);
                    }
                }
            }
            
            if (markedColumnsNew.size() == 0)
            {
                break;
            }
            
            // Step 3. For all new marked columns, mark rows with assigned zeros
            
            if (DEBUG)
            {
                System.out.println("Newly marked columns = " + markedColumnsNew);
            }
            markedRowsNew = new TreeSet<>();
            for (int column : markedColumnsNew)
            {
                for (int row=0; row<working.length; row++)
                {
                    Integer assignedColumn = assignedRows.get(row);
                    if (assignedColumn != null && assignedColumn.intValue() == column)
                    {
                        markedRows.add(row);
                        markedRowsNew.add(row);
                    }
                }
            }
            
            if (markedRowsNew.size() == 0)
            {
                break;
            }

        }
        
        // Create the unmarked rows set
        for (int row=0; row<working.length; row++)
        {
            if (! markedRows.contains(row))
            {
                unmarkedRows.add(row);
            }
        }
        
    }
    
    public void createAdditionalZeros(int[][] working, TreeSet<Integer> unmarkedRows, TreeSet<Integer> markedColumns)
    {
        int minValue = Integer.MAX_VALUE;
        int rows = working.length;
        int columns = working[0].length;
        ArrayList<int[]> doubleCoveredCells = new ArrayList<>();
        ArrayList<int[]> uncoveredCells = new ArrayList<>();
        
        // Find uncovered cells, double covered cells and minimum of uncovered cells
        for (int row=0; row<rows; row++)
        {
            boolean rowSelected = false; 
            if (unmarkedRows.contains(row))
            {
                rowSelected = true;
            }
            for (int column=0; column<columns; column++)
            {
                boolean columnSelected = false;
                if (markedColumns.contains(column))
                {
                    columnSelected = true;
                }
                if (rowSelected && columnSelected)
                {
                    doubleCoveredCells.add(new int[] {row, column});
                }
                if (! rowSelected && ! columnSelected)
                {
                    uncoveredCells.add(new int[] {row, column});
                    if (working[row][column] < minValue)
                    {
                        minValue = working[row][column];
                    }
                }
            }
        }
        
        // Subtract minimum from uncovered cells
        for (int[] toSubtract : uncoveredCells)
        {
            working[toSubtract[0]][toSubtract[1]] -= minValue;
        }
        
        // Add minimum to double covered cells
        for (int[] toAdd : doubleCoveredCells)
        {
            working[toAdd[0]][toAdd[1]] += minValue;
        }

    }
    
    public void rebuildHeaps(int[][] working)
    {
        rowZero = new MinHeap<>(new ZeroPositions[] {});
        colZero = new MinHeap<>(new ZeroPositions[] {});
        int rows = working.length;
        int columns = working[0].length;
        for (int row=0; row<rows; row++)
        {
            for (int column=0; column<columns; column++)
            {
                if (working[row][column] == 0)
                {
                    addZeroToRowHeap(row, column);
                    addZeroToColumnHeap(row, column);
                }
            }
        }
    }
    
    public Hashtable<Integer, Integer> solve()
    {
        int[][] working = copy(matrix);
        Hashtable<Integer, HashSet<Integer>> zeros = new Hashtable<>();
        subtractRowMins(working, zeros, false);
        subtractColMins(working, zeros, false);
        Hashtable<Integer, Integer> assignedRows = new Hashtable<>();
        Hashtable<Integer, HashSet<Integer>> crossedOutZeros = new Hashtable<>();
        HashSet<Integer> unassignableRows = new HashSet<>();
        HashSet<Integer> unassignableColumns = new HashSet<>();
        boolean complete = false;
        while (! complete)
        {
            assignTask(working, assignedRows, crossedOutZeros, unassignableRows, unassignableColumns);
            if (DEBUG)
            {
                print(working);
                System.out.println("Assigned Rows = " + assignedRows);
                System.out.println("Crossed out zeros = " + crossedOutZeros);
            }
            TreeSet<Integer> unmarkedRows = new TreeSet<>();
            TreeSet<Integer> markedColumns = new TreeSet<>();
            drawLines(working, assignedRows, unmarkedRows, markedColumns);
            if (DEBUG)
            {
                System.out.println("Row Lines = " + unmarkedRows);
                System.out.println("Column Lines = " + markedColumns);
            }
            createAdditionalZeros(working, unmarkedRows, markedColumns);
            if (DEBUG)
            {
                System.out.println("Aftre creating additional zeros...");
                print(working);
            }
            rebuildHeaps(working);
            assignedRows = new Hashtable<>();
            crossedOutZeros = new Hashtable<>();
            unassignableRows = new HashSet<>();
            unassignableColumns = new HashSet<>();
            assignTask(working, assignedRows, crossedOutZeros, unassignableRows, unassignableColumns);
            if (DEBUG)
            {
                System.out.println("Assigned Rows = " + assignedRows);
            }
            complete = (assignedRows.size() == working.length);
        }
        return assignedRows;
    }
    
    public ArrayList<Integer> assignments(Hashtable<Integer, Integer> assignedRows)
    {
        ArrayList<Integer> rows = new ArrayList<Integer>(assignedRows.keySet());
        Collections.sort(rows);
        ArrayList<Integer> assignments = new ArrayList<>();
        for (int row : rows)
        {
            assignments.add(matrix[row][assignedRows.get(row)]);
        }
        return assignments;
    }
    
    public int totalCost(ArrayList<Integer> assignments)
    {
        int totalCost = 0;
        for (int cost : assignments)
        {
            totalCost += cost;
        }
        return totalCost;
    }
    
    public int totalCostByIndices(ArrayList<Integer> indices)
    {
        int totalCost = 0;
        for (int row=0; row<indices.size(); row++)
        {
            totalCost += matrix[row][indices.get(row)];
        }
        return totalCost;
    }
    
    public ArrayList<Integer> bruteForce()
    {
        int tasks = matrix.length;
        int i = 0;
        int minCost = 0;
        int noCombinations = 0;
        ArrayList<Integer> solution = null;
        for (ArrayList<Integer> possibleSolution : HammingDistanceUtil.combinations(0, tasks, tasks))
        {
            noCombinations++;
            int cost = totalCostByIndices(possibleSolution);
            if (i == 0)
            {
                minCost = cost;
                System.out.println(possibleSolution);
            }
            else if (cost < minCost)
            {
                minCost = cost;
                solution = possibleSolution;
            }
        }
        System.out.println("Min cost = " + minCost);
        System.out.println("Number of combinations tried = " + noCombinations);
        return solution;
    }
    
    public static void test01(String fileName) throws IOException
    {
        Hungarian hungarian = new Hungarian(fileName, 1);
        Hashtable<Integer, HashSet<Integer>> zeros = new Hashtable<>();
        hungarian.print();
        int[][] sm = hungarian.subtractRowMins(hungarian.matrix, zeros, true);
        hungarian.print(sm);
        sm = hungarian.subtractColMins(sm, zeros, false);
        hungarian.print(sm);
    }
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void test02(String fileName, int matrixNumber) throws IOException
    {
        Hungarian hungarian = new Hungarian(fileName, matrixNumber);
        long start, end = 0;
        start = time();
        Hashtable<Integer, Integer> assignedRows = hungarian.solve();
        ArrayList<Integer> assignments = hungarian.assignments(assignedRows);
        int totalCost = hungarian.totalCost(assignments);
        end = time();
        System.out.println("Assignments = " + assignments);
        System.out.println("Total cost = " + totalCost);
        System.out.println(String.format("Time spent = %d ms", (end - start)));
        
        // Brute Force
        // ArrayList<Integer> solution = hungarian.bruteForce();
    }
    
    public static void main(String[] args) throws Exception
    {
        // test02("data/matrix_01.txt", 1);
        test02("java/data/matrix_03.txt", 5);
    }

}
