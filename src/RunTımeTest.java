import java.math.BigInteger;
import java.security.Key;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stahe on 4/26/2019.
 */
public class RunTımeTest {
    //List<User> members;
    static int M=0;
    static int N=0;
    static int threshold=5;
    public static void main(String[] args) throws Exception {
       /* //sets up a server with the given threshold
        Server s = Server.getInstance(threshold);
        //initialize M members
        for(int i=0;i<M;i++){
            s.Reg(i); //this function creates a user with index i and adds to the server's list of users
        }
        for(int i=0;i<N;i++){
            //the token of the new invitee
            BigInteger[] t=  s.Tgen(i+M);
            //select threshold many inviters randomly from M registered members
            ArrayList<Integer> inviters= selectRandomInviters(M);
            //generate threshold invitations and save into invList
            Inviter inviter = new Inviter();
            Invitation [] invList= new Invitation[threshold];
            for(int j=0;i<inviters.size();j++){
                //generate an invitation from the next inviter
                User u = s.users.get(inviters.get(i));
                Invitation invitation = inviter.Igen(t, u.getShare(), (PublicKey) s.vk, (Key) s.ek);
                invList[j]=invitation;
            }
            //collect the invitations
            Invitee invitee = new Invitee();
            Invitation letter = invitee.Icoll(invList, s.ek, t);

            //verify the invitation
            boolean n = s.verify(letter, t);

            System.out.println("The verdict is: " + n);

        }
        /*
        BigInteger[] t = s.Tgen();

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

    }/*

    public static  ArrayList<Integer> selectRandomInviters(int range)
    {
       ArrayList<Integer> list=new ArrayList<>();
        Random r=new Random();
        for(int i=0; i<threshold;i++)
        {
            int index=r.nextInt(range);
            while(list.contains(index))
            {
                index=r.nextInt(range);
            }
            list.add(index);
        }
        return list;
    }*/
}
