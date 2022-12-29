package algohw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class osman_taha_gunes_2018510033 {
	static int absumresult=0;
	static public NodeTemp DP(NodeTemp root,int sum) {
		
		NodeTemp fakeroot;
		int absum=sum;
		while(root!=null) {
			//System.out.println(root.data+" while giris");
			if(root.child!=null&&!root.child.check)
				root=root.child;//amaç en sol alta inmek
			else if(root.child!=null&&root.child.check&&root.next!=null&&!root.next.check) 
			{
				//System.out.println(root.data+" cocuk ziyaret edilmis");
				root.check=true;
				root=root.next;}
			//çocuðuna gidilmiþse ve komþuya gidilmemiþse
			else if(root.child!=null&&root.child.check&&root.next==null&&root.parent!=null) 
			{//çocuk ziyaret edilmiþse ve komþu yoksa ve ebeveyn varsa yukarý kayýyor
				//System.out.println(root.data+" cocuklar ziyaret edilmis ve komsu yok");
				root.check=true;
				root=root.parent;
			}
			else if(root.parent!=null&&root.child!=null&&root.child.check&&root.next==null){//çocuklarýna gidilmisþe ve komþusu yoska yukarý9
				//System.out.println(root.data+" cocuklara gidilmis komsu yok ebeveyn varsa");
				root=root.parent;
			}
			else if(root.check&&root.next!=null&&!root.next.check) {////nodea gidilmiþ komþusuna gidilmemiþse
				//System.out.println(root.data+" sonraki nodea gecildi");
				root=root.next;
			}
			
			else if(!root.check){//siblingleri toplayýp parentla karþýlaþtýracaðýz
				fakeroot=root;//
				absum=absum+fakeroot.ability;//nodeun yeteneðini aldýk
				root.check=true;
				fakeroot.check=true;
				//way=way+" "+fakeroot.data;
				//System.out.print(">"+fakeroot.data);
				while(fakeroot.next!=null&&!fakeroot.next.check) {//ziyaret edilmemiþse ve yaný doluysa devam edicek
					if(fakeroot.child!=null) {
				//	System.out.println("nodeun cocugu varsa");
					int saveabilities=absum;//
					fakeroot=DP(fakeroot,absum);
					absum=saveabilities;
					}					
					if(fakeroot==null)return null;
					fakeroot=fakeroot.next;
					absum=absum+fakeroot.ability;//abilitysi toplandý
					fakeroot.check=true;//ziyaret edildi
					//way=way+" "+fakeroot.data;//yol kayýt edildi
				    //System.out.print(" "+fakeroot.data);
				}
				
				if(root.parent!=null&&absum<root.parent.ability&&!root.parent.check) {//ebeveynin toplamý geçilemediyse
					//System.out.println(" <ebeveyn büyük geldi> ");
					absum=root.parent.ability;
					root=root.parent;//ebeveyne geçtik
					
					absumresult=absumresult+absum;
					absum=0;
				}
				else if(root.parent==null) {//en tepeye dönüldügünde
					System.out.println(absumresult+root.ability);
					return null;
				}
				else {//ebeveynin toplami geçildiðinde
					root=root.parent;
					root.check=true;
					absumresult=absumresult+absum;
					absum=0;
				}
			}
			else if(root.parent!=null&&root.child==null&&root.next==null) {//çocuðu ve komþusu yoksa ebeveyni varsa ebeveyne geçilir
				root=root.parent;
			}
			else if(root.parent==null) {//en tepeye dönüldügünde
				System.out.println(absumresult+root.ability);
				return null;
			}
		}
		return null;
		
	}
	static public int Greedy(NodeTemp root,ArrayList<NodeTemp> nodes) {
		int max=0,j=0,i=0,counter=0,greedysum=0;
		ArrayList<NodeTemp> BtoL=new ArrayList();
		while(counter!=nodes.size()) {
			max=0;
			for (i = 0; i < nodes.size(); i++) {
				
				if(nodes.get(i).ability>max&&!nodes.get(i).greedyCheck) {
					max=nodes.get(i).ability;//en yükseði bulduk
				}
			}
			for (j = 0; j < nodes.size(); j++) {
				if(nodes.get(j).ability==max&&!nodes.get(j).greedyCheck) {
					nodes.get(j).greedyCheck=true;//en yükseði ziyaretli iþaretledik
					break;
					}
			}
			counter++;
			BtoL.add(nodes.get(j));//yeni listemize büyükten kücüge siraladik
		}
		System.out.print("Greedy hunters : ");
		for (int k = 0; k < BtoL.size(); k++) {
				if(BtoL.get(k).parent!=null&&!BtoL.get(k).parent.gC2) {//coktan aza dogru yeteneklinin ebeveynine bakýldý
					greedysum=greedysum+BtoL.get(k).ability;
					System.out.print(" "+BtoL.get(k).data+"("+BtoL.get(k).ability+")");
					BtoL.get(k).gC2=true;
				}
			}
		System.out.println();
		return greedysum;
		
		//return greedysum;
		
	}
	static class NodeTemp
    {
		boolean check,gohunt,greedyCheck,gC2;
        String data;
        int ability;
        NodeTemp next,child,parent;
        public NodeTemp(String data,int ability)
        {
            this.data = data;
            this.ability=ability;
            gC2=check = gohunt = greedyCheck = false;
            next = child = parent =null;
        }    }
     
    // Adds a sibling to a list with starting with n
    static public NodeTemp addSibling(NodeTemp parent,NodeTemp node, String data,int ability)
    {
    	//System.out.println(node.data+" ya "+data+" sibling eklendi");
        if(node == null)
            return null;
        while(node.next != null)
            node = node.next;
        
        node.next = new NodeTemp(data,ability);
        node.next.parent=parent;
        //if(parent!=null)System.out.println(node.next.data+" in parenti "+parent.data+" ayarlandi sb");
        return(node.next);
    }
         
    // Add child Node to a Node
    static public NodeTemp addChild(NodeTemp node,String data,int ability)
    {
        if(node == null)
            return null;
     
        // Check if child is not empty.
        
        	
        	node.child = new NodeTemp(data,ability);
         	node.child.parent = node;
         //	System.out.println(node.child.data+" nin parenti "+node.data+" ayarlandi ch");
        	return (node.child);
    }
  
 
    // Driver code
   
	public static void main(String[] args) throws FileNotFoundException {
		boolean firstcheck=true,secondfor=false;
		NodeTemp root = null;
		int counter=0,counter1=0;
		ArrayList<NodeTemp> nodes = new ArrayList();
		File f = new File("hunting_abilities.txt");
		Scanner dosya = new Scanner(f);				
		while(dosya.hasNextLine()) {			
			if(counter>0) {
				String[] NandA =dosya.nextLine().split("\t");//yetenek gucu ve isim ayrildi
				int trans=Integer.parseInt(NandA[1]); 
			    nodes.add(new NodeTemp(NandA[0],trans));
			}
			else {
				dosya.nextLine();
				counter++;
			}		}
		dosya.close();
		File f1 = new File("lions_hierarchy.txt");
		Scanner dosya1 = new Scanner(f1);				
		while(dosya1.hasNextLine()) {
			if(counter1!=0) {
				String[] fsr =dosya1.nextLine().split("\t");//dosyadan okunaný splitliyoruz
					//get(k) first node
					//get(i) second node
					secondfor=false;
					for (int k = 0; k < nodes.size(); k++) {
						String node1=nodes.get(k).data;//kontrol etmek icin kayit ediyoruz
						if(fsr[0].equals(node1)) {//first nodeu buluyoruz
							 if(firstcheck) {
							    	root = nodes.get(k);//root ayarlandi
									counter1++;
									//System.out.println(root.data+" root olarak ayarlandi.");
									
							    }
							for (int i = 0; i < nodes.size(); i++) {								
								String node2=nodes.get(i).data;//kontrol etmek icin kayit ediyoruz
								if(fsr[1].equals(node2)) {//second nodeu buluyoruz
									if(fsr[2].equals("Right-Sibling")) {
										//System.out.println(nodes.get(k).data+" ya sibling olarak "+nodes.get(i).data+" isimli node "+nodes.get(i).ability+" yetenegiyle eklendi");
										nodes.set(i, addSibling(nodes.get(k).parent,nodes.get(k),nodes.get(i).data,nodes.get(i).ability));
									}
									else {
										//System.out.println(nodes.get(k).data+" ya child olarak "+nodes.get(i).data+" isimli node "+nodes.get(i).ability+" yetenegiyle eklendi");
										if(firstcheck) {
											nodes.get(i).parent=root;
											firstcheck=false;
										}
										nodes.set(i, addChild(nodes.get(k),nodes.get(i).data,nodes.get(i).ability));
									}secondfor=true;break;/*forlar kapandi*/}								}if(secondfor)break;					}							}											}
			else {
				dosya1.nextLine();
				counter1++;
			}			}
		dosya1.close();
		int i=0;
		for (i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).data.toString().equals(root.data.toString())){
				//System.out.println(nodes.get(i).data+" = "+root.data);
				break;
			}		}
		//traverseTree(nodes.get(i));
		
		DP(nodes.get(i),0);
		System.out.println("Greedy algorithm abilities :"+Greedy(nodes.get(i),nodes));
		}
		
		
		/*System.out.println(“DP Results:” + ( Total Hunting Ability ) );
		 System.out.println(“DP Results- Selected Lions:”);
		 System.out.println(“Greedy Results:” + (Total Hunting Ability) );
		 System.out.println(“Greedy Results- Selected Lions:” );*/
		
}
	

