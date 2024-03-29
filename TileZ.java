import java.io.*;
import java.util.*;

public class TileZ
{
	static int TileArray[] = {1,2,3,8,0,4,7,6,5};
	static int StateArray[] = new int[9];
	static int MovementArray[] = new int[4];
	static int New_BestState[] = new int[9];
	static int INDX = 0;
	static int LEVEL = 1;
	static int store;
	static int pointer = 0;
	static int Best_Tile;
	static int f_dash_val = 1000;
	static int Memory[] = new int[1000];
	
	
	public static void displayState(int Arr[])
	{
		for(int j=0;j<9;j++)
		{
			System.out.print(Arr[j]+" ");
			if(j==2 || j==5)
			{
				System.out.println("\n");
			}
		}
		System.out.print("\n");
	}
	
	
	
	public static int findMisplacedTile(int Array1[],int Array2[])
	{
		int count = 0;
		
		for(int i=0;i<9;i++)
		{
			if(Array1[i]!=Array2[i])
			{
				count++;
			}
		}
		return count;
	}
	
	
	
	public static int get_Tiles_Besides_Empty_Slot(int Array[])
	{
		int count = 0;
		
		if(Array[0]==0)
		{
			//tiles besides index:0 are index:1,3
			MovementArray[INDX] = Array[1];
			INDX++;
			MovementArray[INDX] = Array[3];
			store = 0;
		}
		else if(Array[1]==0)
		{
			//tiles besides index:1 are index:0,2,4
			MovementArray[INDX] = Array[0];
			INDX++;
			MovementArray[INDX] = Array[2];
			INDX++;
			MovementArray[INDX] = Array[4];
			store = 1;
		}
		else if(Array[2]==0)
		{
			//tiles besides index:2 are index:1,5
			MovementArray[INDX] = Array[1];
			INDX++;
			MovementArray[INDX] = Array[5];
			store = 2;
		}
		else if(Array[3]==0)
		{
			//tiles besides index:3 are index:0,4,6
			MovementArray[INDX] = Array[0];
			INDX++;
			MovementArray[INDX] = Array[4];
			INDX++;
			MovementArray[INDX] = Array[6];
			store = 3;
		}
		else if(Array[4]==0)
		{
			//tiles besides index:4 are index:1,3,5,7
			MovementArray[INDX] = Array[1];
			INDX++;
			MovementArray[INDX] = Array[3];
			INDX++;
			MovementArray[INDX] = Array[5];
			INDX++;
			MovementArray[INDX] = Array[7];
			store = 4;
		}
		else if(Array[5]==0)
		{
			//tiles besides index:5 are index:2,4,8
			MovementArray[INDX] = Array[2];
			INDX++;
			MovementArray[INDX] = Array[4];
			INDX++;
			MovementArray[INDX] = Array[8];
			store = 5;
		}
		else if(Array[6]==0)
		{
			//tiles besides index:6 are index:3,7
			MovementArray[INDX] = Array[3];
			INDX++;
			MovementArray[INDX] = Array[7];
			store = 6;
		}
		else if(Array[7]==0)
		{
			//tiles besides index:7 are index:4,6,8
			MovementArray[INDX] = Array[4];
			INDX++;
			MovementArray[INDX] = Array[6];
			INDX++;
			MovementArray[INDX] = Array[8];
			store = 7;
		}
		else if(Array[8]==0)
		{
			//tiles besides index:8 are index:5,7
			MovementArray[INDX] = Array[5];
			INDX++;
			MovementArray[INDX] = Array[7];
			store = 8;
		}
		
		System.out.print("> TILES that can be moved : ");
		for(int i=0;i<=INDX;i++)
		{
			System.out.print(MovementArray[i] + ", ");
			count++;
		}
		return count;
	}
	
	
	
	public static void get_the_f_dash_value(int tile)
	{
		int A[] = new int[9];
		
		for(int k=0;k<9;k++)
		{
			A[k] = StateArray[k];
		}
		
		int j=0;
		while(A[j]!=tile)
		{
			j++;
		}
		A[store] = tile;
		A[j] = 0;
		
		int misplaced = findMisplacedTile(TileArray,A);
		int f_val = LEVEL+misplaced;
		System.out.println("\n\nF(n) value for tile : "+tile+" => "+f_val);
		displayState(A);
		
		if(f_dash_val > f_val)
		{
			f_dash_val = f_val;
			Best_Tile = tile;
			//State with the least Heuristic Value is used for next iterations
			for(int m=0;m<9;m++)
			{
				New_BestState[m] = A[m];
			}
		}
	}
	
	
	
	public static void main(String args[])throws IOException
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("*********8 TILE PUZZLE*********\n\n");
		System.out.println("*********GOAL STATE*********\n");
		displayState(TileArray);
		
		System.out.println("\n\nEnter the TILES Values : \n");
		
		for(int i=0;i<9;i++)
		{
			System.out.print("> Enter Your State for StateArray["+i+"] : ");
			StateArray[i] = sc.nextInt();
		}
		
		System.out.println("\n\n*********YOUR STATE*********");
		displayState(StateArray);
				
		int Num_Misplaced_Tiles = findMisplacedTile(TileArray,StateArray);
		System.out.println("\n\n***************************LEVEL:"+LEVEL+"***************************\n");
		System.out.println("> NUMBER OF MISPLACED TILES : "+Num_Misplaced_Tiles);
		
		//LOOP TILL MISPLACED TILES != 0
		
		while(Num_Misplaced_Tiles!=0)
		{
			//calculate the number of tiles besides 0 and the number of moves possible
			int possible_moves = get_Tiles_Besides_Empty_Slot(StateArray);
			System.out.println("\n> The Number of Possible Moves : "+possible_moves);
			
			
			//working out every move from the collected possible moves
			for(int k=0;k<possible_moves;k++)
			{
				get_the_f_dash_value(MovementArray[k]);
			}
			
			System.out.println("\n\n*****BEST HEURISTIC VALUE : "+f_dash_val);
			Memory[pointer] = Best_Tile;
			pointer++;
			
			System.out.println("\n\n*****BEST STATE WITH LESS HEURISTIC VALUE : ");
			displayState(New_BestState);
			LEVEL++;
			
			for(int m=0;m<9;m++)
			{
				StateArray[m] = New_BestState[m];
			}
			
			Num_Misplaced_Tiles = findMisplacedTile(TileArray,StateArray);
			System.out.println("\n\n***************************LEVEL:"+LEVEL+"***************************\n");
			System.out.println("> NUMBER OF MISPLACED TILES : "+Num_Misplaced_Tiles);
			
			INDX = 0;
			for(int m=0;m<4;m++)
			{
				MovementArray[m] = 0;
			}
			
			f_dash_val = 100;
		}
		
		System.out.println("\n\n###############MOVEMENT TO REACH GOAL STATE###############\n");
		for(int m=0;m<pointer;m++)
		{
			if(m==pointer-1)
			{
				System.out.print(Memory[m]+".");
			}
			else
			{
				System.out.print(Memory[m]+"->");
			}
		}
	}
}
