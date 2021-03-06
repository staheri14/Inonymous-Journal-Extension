import java.math.BigInteger;
import java.security.Key;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Random;

public class Test {
	//List<User> members;
	static int M=100; //number of initial members
	static int N=100; //number of new users to be added


	public static void main(String[] args) throws Exception {

		ArrayList<Time> avgTimeList=new ArrayList<>();
		for (int thr=2;thr<11;thr++)
		{
			Time avgTime=runSampleSystem(thr);
			avgTimeList.add(avgTime);
		}
		print(avgTimeList);

		/*Server s = Server.getInstance(5);
		BigInteger[] t = s.TokenGeneration();
		
		Inviter inv = new Inviter();
		User u2 = s.users.get(2);
		User u4 = s.users.get(4);
		User u7 = s.users.get(7);
		User u8 = s.users.get(8);
		User u9 = s.users.get(9);

		Invitation i2 = inv.Igen(t, u2.getShare(), (PublicKey) s.vk, (Key) s.ek);
		Invitation i4 = inv.Igen(t, u4.getShare(), (PublicKey) s.vk, (Key) s.ek);
		Invitation i7 = inv.Igen(t, u7.getShare(), (PublicKey) s.vk, (Key) s.ek);
		Invitation i8 = inv.Igen(t, u8.getShare(), (PublicKey) s.vk, (Key) s.ek);
		Invitation i9 = inv.Igen(t, u9.getShare(), (PublicKey) s.vk, (Key) s.ek);

		Invitee invitee = new Invitee();
		Invitation[] invitations = { i2, i4, i7, i8, i9 };
		Invitation letter = invitee.Icoll(invitations, s.ek, t);

		boolean n = s.verify(letter, t);

		System.out.println("The veridict is: " + n);*/

	}
	public static  ArrayList<Integer> selectRandomInviters(int range, int thr)
	{
		ArrayList<Integer> list=new ArrayList<>();
		Random r=new Random();
		for(int i=0; i<thr;i++)
		{
			int index=r.nextInt(range);
			while(list.contains(index))
			{
				index=r.nextInt(range);
			}
			list.add(index);
		}
		return list;
	}

	/**
	 *  takes the avg run time of system algorithms for a given threshold
	 * @param threshold
	 * @throws Exception
	 */
	public static Time runSampleSystem(int threshold) throws Exception
	{
		double TgenTime=0,IgenTime=0,IcollTime=0,IverfyTime=0,IpoicTime=0, IpoicVTime=0, RegTime=0, SetupTime=0;
		double startTime=0,endTime=0;
		startTime=System.currentTimeMillis();
		//sets up a server with the given threshold
		Server s = Server.getInstance(threshold);
		endTime=System.currentTimeMillis();
		System.out.println("Initial setup takes: " + (endTime-startTime) +"ms");

		startTime=System.currentTimeMillis();
		s.resetFunction(threshold);
		endTime=System.currentTimeMillis();
		SetupTime+=endTime-startTime;


		//initialize M members
		for(int i=0;i<M;i++){
			startTime=System.currentTimeMillis();
			s.Reg(i); //this function creates a user with index i and adds to the server's list of users
			endTime=System.currentTimeMillis();
			RegTime+=endTime-startTime;
		}
		for(int i=0;i<N;i++){
			startTime=System.currentTimeMillis();
			//the token of the new invitee
			Token t=  s.Tgen(i+M);
			endTime=System.currentTimeMillis();
			TgenTime+=endTime-startTime;


			//select threshold many inviters randomly from M registered members
			ArrayList<Integer> inviters= selectRandomInviters(M, threshold);
			//generate threshold invitations and save into invList
			Inviter inviter = new Inviter();
			Invitation [] invList= new Invitation[threshold];

			for(int j=0;j<inviters.size();j++){
				//generate an invitation from the next inviter
				User u = s.users.get(inviters.get(j));
				startTime=System.currentTimeMillis();
				Invitation invitation = inviter.Igen(t, u.getShare(), (PublicKey) s.vk, (Key) s.ek);
				endTime=System.currentTimeMillis();
				IgenTime+=endTime-startTime;

				POICwitness wit=new POICwitness(u.getShare().getShare(),invitation.getEd()[2],invitation.getDi());
				startTime=System.currentTimeMillis();
				POICproof proof=ZKPOIC.prove(invitation,t,wit);
				endTime=System.currentTimeMillis();
				IpoicTime+=endTime-startTime;


				BigInteger gammai=s.getG().modPow(u.getShare().getShare(),s.getP());

				startTime=System.currentTimeMillis();
				ZKPOIC.verify(invitation, t, gammai, proof);
				endTime=System.currentTimeMillis();
				IpoicVTime+=endTime-startTime;

				invList[j]=invitation;
			}
			//collect the invitations
			Invitee invitee = new Invitee();
			startTime=System.currentTimeMillis();
			Invitation letter = invitee.Icoll(invList, s.ek, t);
			endTime=System.currentTimeMillis();
			IcollTime+=endTime-startTime;


			//verify the invitation
			startTime=System.currentTimeMillis();
			boolean n = s.verify(letter, t);
			endTime=System.currentTimeMillis();
			IverfyTime+=endTime-startTime;

			//System.out.println("The verdict is: " + n);

		}
		//System.out.println("Tgen run time nano: " + TgenTime/N);
		//System.out.println("Igen run time nano: " + IgenTime/(N*threshold));
		//System.out.println("Icoll run time nano: " +  IcollTime/N);
		//System.out.println("Ivrfy run time nano: " + IverfyTime/N);

		Time avgTime=new Time(TgenTime/N, IgenTime/(N*threshold), IcollTime/N, IverfyTime/N, IpoicTime/(N*threshold), IpoicVTime/(N*threshold), RegTime/M, SetupTime);

		return avgTime;
	}

	public static void print (ArrayList<Time> avgTimeList)
	{
		System.out.println("\nTgen:");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getTgen()+"\t ");
		}

		System.out.println("\nIgen");
		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getIgen()+"\t ");
		}
		System.out.println("\nIcoll");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getIcoll()+"\t ");
		}
		System.out.println("\nIvrfy");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getIVrfy()+"\t ");
		}

		System.out.println("\nPOIC");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getIpoic()+"\t ");
		}

		System.out.println("\nVerification of POIC");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getIpoicV()+"\t ");
		}

		System.out.println("\nReg");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getReg()+"\t ");
		}

		System.out.println("\nRecalculating the polynomial takes");

		for(int i=0;i<avgTimeList.size();i++)
		{
			System.out.print(avgTimeList.get(i).getSetup()+"\t ");
		}
	}
}
