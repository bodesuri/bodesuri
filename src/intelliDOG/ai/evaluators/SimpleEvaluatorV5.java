package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.Rules;
import intelliDOG.ai.utils.DebugMsg;

import java.util.ArrayList;
/**
 * The V5 measures checks now also if the enemy tokens are 
 * near their heaven.
 */
public class SimpleEvaluatorV5 implements Evaluator {

	//positive influences
	final private int myPawnsOnField = 10;
	final private int myPawnsInHeaven = 150;
	final private int myAllyOnField = 30;
	final private int myAllyInHeaven = 40;
	final private int myEnemyOffField = 60;
	final private int myStartpointOccupied = 30;
	final private int enemyInFrontUpTo7 = 20;
	final private int enemyInFrontUpTo13 = 10;
	final private int enemyBehind4 = 10;
	final private float distanceToHeaven = 1.2f; 
	final private int distanceInHeaven = 10;
	final private float distanceForAlly = 0.8f; 
	final private int allInHeaven = 2000;
	final private int staticHeaven = 250; 
	final private int gameWon = 6000;
	//negative influences
	final private int enemyBehindUpTo7 = -20;
	final private int enemyBehindUpTo13 = -10;
	final private int enemyOnEdgeOfHeaven = -40;
	final private int enemyInHeaven = -40;
	//card weights
	final private int jokerWeight = 5;
	final private int aceWeight = 3;
	final private int fourSevenJackKingWeight = 2;
	final private int otherCardWeight = 0; 
	
	//support variables 
	private DebugMsg msg = DebugMsg.getInstance(); 
	private ArrayList<Integer> myPawns = null;
	private ArrayList<Integer> alliedPawns = null;
	private ArrayList<Integer> enemyPawns = null;  
	
	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player,
			int[] card) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int[] cards, float fading) {
		msg.debug(this, "fading factor is: " + fading);
		Rules r = Rules.getInstance();
		int sum = 0; 
		if(r.allPiecesInHeavenOfPlayer(targetState, player))
		{
			sum = sum + allInHeaven;
			msg.debug(this, "all own pawns are in heaven rewared: "+staticHeaven);
			player = (byte) r.getPartnerForPlayer(player);
			if(r.allPiecesInHeavenOfPlayer(targetState, player))
			{
				//game won!
				return gameWon; 
			}
		}
		byte alliedplayer = r.getPartnerForPlayer(player);
		if(alliedplayer == 0)
			alliedplayer = 4; 
		
		int myHome = r.getHomePositionForPlayer(player);
		int alliedHome = r.getHomePositionForPlayer(alliedplayer);
		byte enemyPlayerOne = (byte) (player +1);
		if(enemyPlayerOne == 5)
		{
			enemyPlayerOne = 1; 
		}
		byte enemyPlayerTwo =   r.getPartnerForPlayer(enemyPlayerOne);
		int enemyHomeOne = r.getHomePositionForPlayer(enemyPlayerOne);
		int enemyHomeTwo = r.getHomePositionForPlayer(enemyPlayerTwo);
		

		myPawns = new ArrayList<Integer>();
		alliedPawns = new ArrayList<Integer>();
		enemyPawns = new ArrayList<Integer>();
		
		//add the positions of each pawn into the datastructure
		for(int i =0; i<80; i++)
		{
			//normal field.. 
			if(targetState[i] == 0)
			{
				//do nothing 
			}
			//check for the protected fields and add them to the right player
			else if(targetState[i] == 5)
			{
				//the only own protected are in the heaven or on my home field
				if(i == myHome || (i >= (64 + ((player-1)*4)) && i<= (68 + (player-1)*4)))
				{
					myPawns.add(i);
				}
				else if(i == alliedHome || (i >= (64 + ((alliedplayer -1)*4)) 
						&& i<= (68 + (alliedplayer -1)*4)))
				{
					alliedPawns.add(i);
				}
				else
				{
					enemyPawns.add(i);
				}
			}
			//check for own tokens
			else if(targetState[i] == player )
			{
				myPawns.add(i);
			}
			//check for allied tokens
			else if((targetState[i] == alliedplayer))
			{
				alliedPawns.add(i);
			}
			//else, it must be an enemy token
			else //if(targetState[i] % 2 != switcher || i >= 64)
			{
				enemyPawns.add(i);
			}
		}
		//check the game for pawns of the player
		sum = sum + (myPawns.size() * myPawnsOnField);
		//check for pawns in the heaven
		for(int i = 0; i<myPawns.size(); i++)
		{
			if(myPawns.get(i) >= 64)
			{
				sum = sum + myPawnsInHeaven;
			}
		}
		msg.debug(this, "After own Pawns on field and in heaven: " + sum);
		//check for allied pawns on the field
		sum = sum + (alliedPawns.size() * ((int) (myAllyOnField * fading)));
		//check for allied pawns in the heaven
		msg.debug(this, "After Allied Pawns on field: " + sum);
		for(int i = 0; i<alliedPawns.size(); i++)
		{
			if(alliedPawns.get(i) >= 64)
			{
				sum = sum + myAllyInHeaven;
			}
		}
		msg.debug(this, "After Allied Pawns in heaven: " + sum);
		//check how many enemy pawns are off the game
		sum = sum + ((8-enemyPawns.size()) * ((int) (myEnemyOffField * fading)));
		msg.debug(this, "After checking if enemy pawns are off the field: " + sum);
		//check if the own starting point is occupied
		if(targetState[myHome] == 5)
		{
			sum = sum + myStartpointOccupied;
		}
		msg.debug(this, "After checking own startpoint: " + sum);
		//check for each own pawn if..
		for(int i = 0; i< myPawns.size(); i++)
		{
			int pos = myPawns.get(i);
			msg.debug(this, "the actual pos is: " + pos); 
			if(myPawns.get(i)<64)
			{
				for(int j = 1; j<14; j++)
				{
					int playerInFront = targetState[(pos + j) %64] ;
					int playerBehind = targetState[(64 + pos - j) %64];
					if(playerInFront != 0 || playerBehind != 0)
					{
						//enemy pawns are in front of (near)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo || 
								playerInFront == 5) && j < 8 && j != 4)
						{
							sum = sum + (int) (enemyInFrontUpTo7 * fading);
						}
						//enemy pawns are in front of (far)
						if((playerInFront ==  enemyPlayerOne || playerInFront == enemyPlayerTwo ||
								playerInFront == 5) && j > 7)
						{
							sum = sum + (int) (enemyInFrontUpTo13 * fading);
						}
						if(pos != myHome)
						{
							//enemy pawns are behind (near)
							int field = (64 + pos - j) %64;
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo ||
									(playerBehind == 5 &&
									(field == enemyHomeOne || field == enemyHomeTwo))) && j < 8)
							{
								sum = sum + (int) (enemyBehindUpTo7 * fading);
							}
							//enemy pawns are behind (far)
							if((playerBehind ==  enemyPlayerOne || playerBehind == enemyPlayerTwo ||
									(playerBehind == 5 && 
									(field == enemyHomeOne || field == enemyHomeTwo))) && j > 7)
							{
								sum = sum + (int) (enemyBehindUpTo13 * fading);
							}
						}
					}
				}
				//a enemy pawn is right 4 steps behind
				if(targetState[(64 +pos -4) % 64] == enemyPlayerOne ||
						targetState[(64 +pos -4) % 64] == enemyPlayerTwo)
				{
					sum = sum + enemyBehind4;
				}
				msg.debug(this, "After wheigting pawn on pos " + pos + ": " + sum);
				//measure the distance with an exponential algorithm
				int distance = pos - ((player -1) * 16);
				if(distance <=0)
				{
					distance = 64 + distance; 
				}
				if(pos % 16 != 0 || (pos == myHome && targetState[pos] != 5))
				{
					msg.debug(this,"the distance is wheighted for pawn at " + pos +
							" as: " + (Math.pow(distance, distanceToHeaven)));
					sum = sum + (int) (Math.pow(distance, distanceToHeaven)); 
				}
			}
			else
			{
				int temp = ((pos -64)%4) + 1;
				sum  = sum + ((temp * distanceInHeaven) + staticHeaven);
				msg.debug(this,"the distance is wheighted for pawn at " + pos +
						" as: " + (temp * distanceInHeaven +staticHeaven));
			}
		}
		//check for enemy pawns in the heaven 
		for(int i = 0; i< enemyPawns.size(); i++)
		{
			if(enemyPawns.get(i) >= 64)
			{
				sum = sum + enemyInHeaven;
			}
		}
		msg.debug(this, "after enemy in heaven: " + sum);
		//measure the own cards
		int j = 0; 
		while(cards[j] != -1)
		{
			int card = cards[j];
			if(card == Cards.JOKER)
				sum = sum + jokerWeight;
			else
			{
				card = cards[j] % 13;
				if(card == Cards.HEARTS_ACE)
				{
					sum = sum + aceWeight;
				}
				else if(card == Cards.HEARTS_FOUR ||
						card == Cards.HEARTS_SEVEN || 
						card == Cards.HEARTS_JACK ||
						card == 0)
				{
					sum = sum + fourSevenJackKingWeight;
				}
				else 
				{
					sum = sum + otherCardWeight;
				}
			}
			j++;
		}
		msg.debug(this, "after card weigthing: " + sum);
		//check how near the allied pawns to their heaven are
		for(int i = 0; i< alliedPawns.size(); i++)
		{
			int pos = alliedPawns.get(i);
			if( pos > 63)
			{
				break; 
			}
			else
			{
				
				int distance = (pos - alliedHome)%64;
				if(distance <0)
				{
					distance = 64 + distance; 
				}
				sum = sum + (int) (distance * ((int) distanceForAlly*fading));
			}
		}
		msg.debug(this, "score after weighting allys advance: "+ sum);
		//check if a enemy is near its heaven
		for(int i =1; i<14; i++)
		{
			int tempOne = (64 +enemyHomeOne - i)%64;
			int tempTwo = (64 +enemyHomeTwo - i)%64;
			if(targetState[tempOne] == enemyPlayerOne)
			{
				sum = sum + enemyOnEdgeOfHeaven;
			}
			if(targetState[tempTwo] == enemyPlayerTwo)
			{
				sum = sum + enemyOnEdgeOfHeaven;
			}
		}
		msg.debug(this, "score after weighting the enemies advance: "+ sum);
		msg.debug(this, "The final score is: " + sum);
		msg.debug(this, "===========================");
		return sum; 
	}
	
	/**
	 * for debugging purposes
	 */
	public void  printPawns()
	{
		System.out.print("mypawns: ");
		for(int i = 0; i< myPawns.size(); i++)
		{
			
			System.out.print(myPawns.get(i) + " ");
		}
		System.out.print("alliedPawns: ");
		for(int i = 0; i< alliedPawns.size(); i++)
		{
			
			System.out.print(alliedPawns.get(i) + " ");
		}
		System.out.print("enemyPawns: ");
		for(int i = 0; i< enemyPawns.size(); i++)
		{
			
			System.out.print(enemyPawns.get(i) + " ");
		}
		System.out.println();
	}

}
