import java.util.*;

public class HundirLaFlota {

	public static void main(String[] args) {
		Scanner teclat = new Scanner(System.in);
		
		//VARIABLES NECESARIS PER EL MAIN
		int seleccio, opcioMenu, cont, jugadorActual= 2, vaixellsEnfonsatsJ1= 0, vaixellsEnfonsatsJ2= 0, vaixellsEnfonsatsM= 0, vaixellsEnfonsatsJ= 0;
		char[][] matriu1= null, matriu2= null, matriuOcultaJ1, matriuOcultaJ2, matriuOcultaJ, matriuOcultaM;
		boolean acertat, jaTocada, fora;
		int fila, columna;
		char filaChar;
		
		//SELECCIO DEL MODE DE JOC, NOMES ES POT TRIAR EL PRIMER COP
		cls();
		System.out.println("   ╔═════════════════╗");
		System.out.println("   ║   MODE DE JOC   ║");
		System.out.println("   ╚═════════════════╝");
		System.out.println();
		System.out.println("[1] 1 Jugador (jugador VS maquina)");
		System.out.println("[2] 2 Jugadors (jugador VS jugador)");
		seleccio= teclat.nextInt();
		cls();
		
		//FILTRE QUE CONTROLA QUE LA OPCIO INTRODUIDA DEL MODE DE JOC SIGUI CORRECT, MENTRE NO SIGUI AIXI TORNARA A DEMANAR
		while((seleccio != 1) && (seleccio != 2)){
			
			System.out.println("   ╔═════════════════╗");
			System.out.println("   ║   MODE DE JOC   ║");
			System.out.println("   ╚═════════════════╝");
			System.out.println();
			System.out.println("[1] 1 Jugador (jugador VS maquina)");
			System.out.println("[2] 2 Jugadors (jugador VS jugador)");
			seleccio= teclat.nextInt();
			cls();
		}
		
		/*     -------------------- INICI JUGADOR VS MAQUINA --------------------     */
		if(seleccio == 1){
			
			//IMPRIMEIX MENU I RETORNA OPCIO TRIADA
			opcioMenu= menu(teclat);
			
			//MENTRE NO S'HAGI TRIAT LA OPCIO SORTIR, ES POT JUGAR LES VEGADES QUE ES VULGUI
			while(opcioMenu != 3){
				
				switch (opcioMenu){
				
				
				case 1: /*POSAR VAIXELLS*/
					
					matriu1= omplirMatriu();
					matriu2= omplirMatriu();
					
					instruccionsColocacio();
					continuar();
					
					/*LA MAQUINA OMPLE EL SEU TAULELL DE JOC*/
					cont= 1;
					while(cont <= 4){
						
						posarVaixellMaquina(matriu2, cont);
						cont++;
					}
					cls();
					System.out.println("AQUEST ES EL TAULELL DE LA MAQUINA");
					System.out.println();
					mostrarMatriu(matriu2);
					System.out.println();
					continuar();
					
					/*EL JUGADOR OMPLE EL SEU TAULELL DE JOC*/
					System.out.println();
					System.out.println("ES EL TEU TORN! OMPLE EL TEU TAULELL DE JOC!");
					System.out.println();
					
					cont= 1;
					while(cont <= 4){
						
						posarVaixell(teclat, matriu1, cont);
						cont++;
					}
					cls();
					System.out.println("AQUEST ES EL TEU TAULELL");
					System.out.println();
					mostrarMatriu(matriu1);
					System.out.println();
					continuar();
				break;
				
				
				case 2: /*JUGAR PARTIDA*/					
					
					//FILTRE QUE ASEGURA QUE SI ES COMENÇA A JUGAR, LA MATRIU HAGI ESTAT OMPLERTA PREVIAMENT
					if (matriu1 == null){
						
						System.out.println("ATENCIO! Abans de començar a jugar has d'omplir els taulells!");
					}else{
						
						matriuOcultaJ= omplirMatriuOculta();
						matriuOcultaM= omplirMatriuOculta();
						
						instruccionsJugar();
						continuar();
						
						//MENTRE QUE NO S'HAGIN TOCAT TOTES LES PARTS DELS VAIXELLS CONTINUARA
						while((vaixellsEnfonsatsM < 20) && (vaixellsEnfonsatsJ < 20)){
							
							//CONTROL DE TIRADA DE JUGADOR
							if((jugadorActual%2) == 0){
								
								cls();
								System.out.println("  TAULELL MAQUINA");
								System.out.println();
								mostrarMatriu(matriuOcultaM);
								
								fila= random();
								columna= random();
								
								//COMPROBA SI LA CASELLA JA HA ESTAT TOCADA PREVIAMENT
								jaTocada= comprobarTirada (matriuOcultaM, fila, columna);
								
								//COMPROVA SI LA CASELLA S'HA ACERTAT
								acertat= tirada(matriuOcultaM, matriu1, fila, columna);
								
								//SI LA CASELLA TRIADA HA TOCAT AIGUA, PASA AL ALTRE JUGADOR, SINO, SUMA UN PUNT DE VAIXELL
								if(acertat == false){
									
									jugadorActual++;
								}else{
									
									if(jaTocada == false){
										
										vaixellsEnfonsatsM++;
									}
									
									//SI EL JUGADOR HA ARRIVAT A 20 PARTS DE VAIXELL TOCADES, VOLDRA DIR QUE ELS HA TROBAT TOT, I PER TANT, GUANYA
									if(vaixellsEnfonsatsM == 20){
										
										cls();
										mostrarMatriu(matriuOcultaM);
										System.out.println();
										System.out.println("HA GUANYAT LA MAQUINA =( ...");
										System.out.println();
										continuar();
									}
								}
							}
							if((jugadorActual%2) != 0){
								
								cls();
								System.out.println("  TAULELL JUGADOR");
								System.out.println();
								mostrarMatriu(matriuOcultaJ);
								
								System.out.print("Indica fila: ");
								filaChar= teclat.next().charAt(0);
								fila= transformar(filaChar);
								System.out.print("Indica columna: ");
								columna= teclat.nextInt();
								columna= columna-1;
								
								//FUNCIO QUE COMPROVA QUE LA FILA I LA COLUMNA NO SURTIN DE TAULELL
								fora= sortidaTaulell (matriuOcultaJ, fila, columna);
								
								//SI SURTEN DE TAULELL, TORNA A INTRODUIR
								while(fora == true){
									
									System.out.println();
									System.out.println("Has introduit una posicio fora de rang, siusplau, torna a introduir:");
									System.out.print("Indica fila: ");
									filaChar= teclat.next().charAt(0);
									fila= transformar(filaChar);
									System.out.print("Indica columna: ");
									columna= teclat.nextInt();
									columna= columna-1;
									
									fora= sortidaTaulell (matriuOcultaJ, fila, columna);
								}
								
								jaTocada= comprobarTirada (matriuOcultaJ, fila, columna);
								
								acertat= tirada(matriuOcultaJ, matriu2, fila, columna);
								
								if(acertat == false){
									
									jugadorActual++;
								}else{
									
									if(jaTocada == false){
										
										vaixellsEnfonsatsJ++;
									}
	
									if(vaixellsEnfonsatsJ == 20){
										
										cls();
										mostrarMatriu(matriuOcultaJ);
										System.out.println();
										System.out.println("HAS GUANYAT A LA MAQUINA !!");
										System.out.println();
										continuar();
									}
								}
							}
						}
						//REINICIALITZACIO DE LES VARIABLES NECESARIES PER TAL DE TORNAR A JUGAR
						vaixellsEnfonsatsJ= 0;
						vaixellsEnfonsatsM= 0;
						jugadorActual= 2;
					}
				}
				opcioMenu= menu(teclat);
			}
			
			
			if(opcioMenu==3){ /*SORTIR*/
				
				System.out.println();
				System.out.println("HAS SORTIT DEL JOC");
			}
			
		}
		/*     -------------------- FINAL JUGADOR VS MAQUINA --------------------     */
		
		/*     -------------------- INICI JUGADOR VS JUGADOR --------------------     */
		if(seleccio == 2){
			
			opcioMenu= menu(teclat);

			while(opcioMenu != 3){
				
				switch (opcioMenu){
				
				
				case 1: /*POSAR VAIXELLS*/
					
					matriu1= omplirMatriu();
					matriu2= omplirMatriu();
					
					instruccionsColocacio();
					continuar();
					
					/*EL JUGADOR 1 OMPLE EL SEU TAULELL DE JOC*/
					System.out.println();
					System.out.println();
					System.out.println("JUGADOR 1, OMPLE EL TEU TAULELL DE JOC!");
					System.out.println();
					
					cont= 1;
					while(cont <= 4){
						
						posarVaixell(teclat, matriu1, cont);
						cont++;
					}
					cls();
					System.out.println("AQUEST ES EL TEU TAULELL, JUGADOR 1");
					System.out.println();
					mostrarMatriu(matriu1);
					System.out.println();
					continuar();
					
					
					/*EL JUGADOR 2 OMPLE EL SEU TAULELL DE JOC*/
					System.out.println();
					System.out.println("JUGADOR 2, OMPLE EL TEU TAULELL DE JOC!");
					System.out.println();
					
					cont= 1;
					while(cont <= 4){
						
						posarVaixell(teclat, matriu2, cont);
						cont++;
					}
					cls();
					System.out.println("AQUEST ES EL TEU TAULELL, JUGADOR 2");
					System.out.println();
					mostrarMatriu(matriu2);
					System.out.println();
					continuar();
	
					
				break;
				
				
				case 2: /*JUGAR PARTIDA*/
					
					//FILTRE QUE ASEGURA QUE SI ES COMENÇA A JUGAR, LA MATRIU HAGI ESTAT OMPLERTA PREVIAMENT
					if (matriu1 == null){
						
						System.out.println("ATENCIO! Abans de començar a jugar has d'omplir els taulells!");
					}else{
						
						matriuOcultaJ1= omplirMatriuOculta();
						matriuOcultaJ2= omplirMatriuOculta();
						
						instruccionsJugar();
						continuar();
						
						while((vaixellsEnfonsatsJ1 < 20) && (vaixellsEnfonsatsJ2 < 20)){
							
							if((jugadorActual%2) == 0){
								
								cls();
								System.out.println("  TAULELL JUGADOR 1");
								System.out.println();
								mostrarMatriu(matriuOcultaJ1);
								
								//Llegir files i columnes, ho hagues fet dins de la funcio 'tirada', pero d'aquesta manera perdia 
								//les files i columnes al programa principal, i les necesito per a la funcio 'comprobarTirada'
								System.out.print("Indica fila: ");
								filaChar= teclat.next().charAt(0);
								fila= transformar(filaChar);
								System.out.print("Indica columna: ");
								columna= teclat.nextInt();
								columna= columna-1;
								
								fora= sortidaTaulell (matriuOcultaJ1, fila, columna);
								
								while(fora == true){
									
									System.out.println();
									System.out.println("Has introduit una posicio fora de rang, siusplau, torna a introduir:");
									System.out.print("Indica fila: ");
									filaChar= teclat.next().charAt(0);
									fila= transformar(filaChar);
									System.out.print("Indica columna: ");
									columna= teclat.nextInt();
									columna= columna-1;
									
									fora= sortidaTaulell (matriuOcultaJ1, fila, columna);
								}
								
								jaTocada= comprobarTirada (matriuOcultaJ1, fila, columna);
								
								acertat= tirada(matriuOcultaJ1, matriu2, fila, columna);
								
								if(acertat == false){
									
									jugadorActual++;
								}else{
									
									if(jaTocada == false){
										
										vaixellsEnfonsatsJ1++;
									}
									
									if(vaixellsEnfonsatsJ1 == 20){
										
										cls();
										mostrarMatriu(matriuOcultaJ1);
										System.out.println();
										System.out.println("HAS GUANYAT, JUGADOR 1 !!");
										System.out.println();
										continuar();
									}
								}
							}
							if((jugadorActual%2) != 0){
								
								cls();
								System.out.println("  TAULELL JUGADOR 2");
								System.out.println();
								mostrarMatriu(matriuOcultaJ2);
								
								System.out.print("Indica fila: ");
								filaChar= teclat.next().charAt(0);
								fila= transformar(filaChar);
								System.out.print("Indica columna: ");
								columna= teclat.nextInt();
								columna= columna-1;
								
								fora= sortidaTaulell (matriuOcultaJ2, fila, columna);
								
								while(fora == true){
									
									System.out.println();
									System.out.println("Has introduit una posicio fora de rang, siusplau, torna a introduir:");
									System.out.print("Indica fila: ");
									filaChar= teclat.next().charAt(0);
									fila= transformar(filaChar);
									System.out.print("Indica columna: ");
									columna= teclat.nextInt();
									columna= columna-1;
									
									fora= sortidaTaulell (matriuOcultaJ2, fila, columna);
								}
								
								jaTocada= comprobarTirada (matriuOcultaJ2, fila, columna);
								
								acertat= tirada(matriuOcultaJ2, matriu1, fila, columna);
								
								if(acertat == false){
									
									jugadorActual++;
								}else{
									
									if(jaTocada == false){
										
										vaixellsEnfonsatsJ2++;
									}
	
									if(vaixellsEnfonsatsJ2 == 20){
										
										cls();
										mostrarMatriu(matriuOcultaJ2);
										System.out.println();
										System.out.println("HAS GUANYAT, JUGADOR 2 !!");
										System.out.println();
										continuar();
									}
								}
							}
						}
						vaixellsEnfonsatsJ1= 0;
						vaixellsEnfonsatsJ2= 0;
						jugadorActual= 2;
					}
				}
				opcioMenu= menu(teclat);
			}
			
			
			if(opcioMenu==3){ /*SORTIR*/
				
				System.out.println();
				System.out.println("HAS SORTIT DEL JOC");
			}
		}
		/*     -------------------- FINAL JUGADOR VS JUGADOR --------------------     */
	}
	/*     ---------------------------------------- FINAL DEL MAIN ----------------------------------------     */
	
	
	
	
	
	//FUNCIO QUE MOSTRA EL MENU I RETORNA LA OPCIO SELECCIONADA
	static int menu (Scanner teclat){
		int opcio;
		
		System.out.println();
		System.out.println("   ╔══════════════════╗");
		System.out.println("   ║  MENU PRINCIPAL  ║");
		System.out.println("   ╚══════════════════╝");
		System.out.println();
		System.out.println("[1] Posar Vaixells");
		System.out.println("[2] Jugar Partida");
		System.out.println("[3] Sortir");
		opcio= teclat.nextInt();
		cls();
		
		return opcio;
	}
	
	//FUNCIO QUE GENERA UN NUMERO ALEATORI ENTER ENTRE 0 I 9
	static int random (){
		int aleatori;
		double num;
		
		num= Math.random();
		num= num*10;
		aleatori= (int) num;
		
		return aleatori;
	}
	
	//FUNCIO QUE OMPLE LA MATRIU INICIAL DE MANERA "BUIDA", AMB "AIGUA"
	static char[][] omplirMatriu (){
		int f, c;
		char[][] matriu= new char [10][10];
		
		for(f=0; f<matriu.length; f++){
			for(c=0; c<matriu.length; c++){
				
				matriu[f][c]= '·'; 
			}
		}
		
		return matriu;
	}
	
	//FUNCIO QUE OMPLE LA MATRIU OCULTA
	static char[][] omplirMatriuOculta (){
		int f, c;
		char[][] matriu= new char [10][10];
		
		for(f=0; f<matriu.length; f++){
			for(c=0; c<matriu.length; c++){
				
				matriu[f][c]= '■'; 
			}
		}
		
		return matriu;
	}
	
	//FUNCIO QUE IMPRIMEIX MATRIU
	static void mostrarMatriu (char[][] matriu){
		int i;
		
		System.out.print(" ");
		for(i=0; i<matriu.length; i++){
			System.out.print(" " + (i+1));
		}
		System.out.println();
		i=0;
		for(int f=0; f<matriu.length; f++){
			for(int c=0; c<matriu.length; c++){
				if(c==0){
					System.out.print(transformarInv(i)+ " ");
					i++;
				}
				System.out.print(matriu[f][c]+ " ");
			}
			System.out.println();
		}
	}
	
	
	
	/*     -------------------- INICI COLOCACIO VAIXELLS JUGADOR --------------------     */
	
	
	
	//FUNCIO QUE DEMANA TOTS ELS VAIXELLS DEL JOC I ELS COLOCA A LA MATRIU INICIALITZADA EN L'ANTERIOR FUNCIO
	static void posarVaixell (Scanner teclat, char[][] matriu, int vaixell){
		char filaChar, direccioChar;
		int fila, columna, cont= 1, sentit=0, direccio=0;
		
		if(vaixell == 1){
			
			System.out.println("Introdueix la posicio inicial del Portaavions:");
			
			System.out.print("Fila: ");
			filaChar= teclat.next().charAt(0);
			fila= transformar(filaChar);
			System.out.print("Columna: ");
			columna= teclat.nextInt();
			columna= columna-1;
			
			//FUNCIO QUE RETORNA LA DIRECCIO EN LA QUE ES COLOCARA EL VAIXELL
			direccioChar= demanarDireccio();
			
			//SEGONS LA DIRECCIO INTRODUIDA ANTERIORMENT, ES COLOCARA D'UNA MANER O D'UN ALTRA
			if(direccioChar == 'w'){ /*CAP A DALT*/
				
				sentit= 7;
				direccio= 7;
			}
			if(direccioChar == 'a'){ /*ESQUERRA*/
				
				sentit= 4;
				direccio= 7;
			}
			if(direccioChar == 's'){ /*CAP A BAIX*/
				
				sentit= 7;
				direccio= 4;
			}
			if(direccioChar == 'd'){ /*DRETA*/
				
				sentit= 4;
				direccio= 4;
			}
			//COLOCA LA PRIMERA POSICIO DEL VAIXELLX
			matriu[fila][columna]= 'A';
			//COLOCA LA RESTA DEL VAIXELL SEGONS LA DIRECCIO I SENTIT INTRODUIDES ANTERIORMENT
			colocarPortavions(matriu, fila, columna, 'A', sentit, direccio);
			
			cls();
			mostrarMatriu(matriu);
				
		}
		if(vaixell == 2){
			
			System.out.println("Introdueix les posicions dels dos Cuirassats:");
			while(cont<=2){
				
				System.out.println("Posicio inicial del curiassat num " + cont + ":");
				
				System.out.print("Fila: ");
				filaChar= teclat.next().charAt(0);
				fila= transformar(filaChar);
				System.out.print("Columna: ");
				columna= teclat.nextInt();
				columna= columna-1;
				
				//FILTRE QUE ASSEGURA QUE LA POSICIO INICIAL DEL VAIXELL NO ES SOLAPI AMB CAP ALTRE VAIXELL I NO SURTI FORA DEL TAULELL
				while(filtrarCasella(matriu, fila, columna)){
					
					System.out.println("Has introduit una posicio inicial ocupada o fora de rang, siusplau, torna a introduir:");
					System.out.print("Fila: ");
					filaChar= teclat.next().charAt(0);
					fila= transformar(filaChar);
					System.out.print("Columna: ");
					columna= teclat.nextInt();
					columna= columna-1;
				}
				
				direccioChar= demanarDireccio();
				
				if(direccioChar == 'w'){
					
					sentit= 7;
					direccio= 7;
				}
				if(direccioChar == 'a'){
					
					sentit= 4;
					direccio= 7;
				}
				if(direccioChar == 's'){
					
					sentit= 7;
					direccio= 4;
				}
				if(direccioChar == 'd'){
					
					sentit= 4;
					direccio= 4;
				}
				matriu[fila][columna]= 'C';
				colocarCuirassats(matriu, fila, columna, 'C', sentit, direccio, 's');
				
				cls();
				mostrarMatriu(matriu);
				
				cont++;
			}
		}
		if(vaixell == 3){
			
			System.out.println("Introdueix les posicions de les tres Fragates:");
			while(cont<=3){
				
				System.out.println("Posicio inicial de la fragata num " + cont + ":");
				
				System.out.print("Fila: ");
				filaChar= teclat.next().charAt(0);
				fila= transformar(filaChar);
				System.out.print("Columna: ");
				columna= teclat.nextInt();
				columna= columna-1;
				
				//FILTRE QUE ASSEGURA QUE LA POSICIO INICIAL DEL VAIXELL NO ES SOLAPI AMB CAP ALTRE VAIXELL I NO SURTI FORA DEL TAULELL
				while(filtrarCasella(matriu, fila, columna)){
					
					System.out.println("Has introduit una posicio inicial ocupada o fora de rang, siusplau, torna a introduir:");
					System.out.print("Fila: ");
					filaChar= teclat.next().charAt(0);
					fila= transformar(filaChar);
					System.out.print("Columna: ");
					columna= teclat.nextInt();
					columna= columna-1;
				}
				
				direccioChar= demanarDireccio();
				
				if(direccioChar == 'w'){
					
					sentit= 7;
					direccio= 7;
				}
				if(direccioChar == 'a'){
					
					sentit= 4;
					direccio= 7;
				}
				if(direccioChar == 's'){
					
					sentit= 7;
					direccio= 4;
				}
				if(direccioChar == 'd'){
					
					sentit= 4;
					direccio= 4;
				}
				matriu[fila][columna]= 'F';
				colocarFragates(matriu, fila, columna, 'F', sentit, direccio, 's');
				
				cls();
				mostrarMatriu(matriu);
				
				cont++;
			}
		}
		if(vaixell == 4){
			
			System.out.println("Introdueix les posicions dels cuatre Patrullers:");
			while(cont<=4){
				
				System.out.println("Patruller num " + cont + ":");
				
				System.out.print("Fila: ");
				filaChar= teclat.next().charAt(0);
				fila= transformar(filaChar);
				System.out.print("Columna: ");
				columna= teclat.nextInt();
				columna= columna-1;
				
				//FILTRE QUE ASSEGURA QUE LA POSICIO INICIAL DEL VAIXELL NO ES SOLAPI AMB CAP ALTRE VAIXELL I NO SURTI FORA DEL TAULELL
				while(filtrarCasella(matriu, fila, columna)){
					
					System.out.println("Has introduit una posicio inicial ocupada o fora de rang, siusplau, torna a introduir:");
					System.out.print("Fila: ");
					filaChar= teclat.next().charAt(0);
					fila= transformar(filaChar);
					System.out.print("Columna: ");
					columna= teclat.nextInt();
					columna= columna-1;
				}
				
				matriu[fila][columna]= 'P';
				
				cls();
				mostrarMatriu(matriu);
				
				cont++;
			}
		}
	}
	
	//FUNCIO QUE COLOCA EL PORTAAVIONS A LA MATRIU SEGONS DIRECCIO I SENTIT
	static void colocarPortavions(char[][] matriu, int fila, int columna, char charVaixell, int sentit, int direccio){
		
		//COLOCACIO DEL VAIXELL
		if(sentit<=5){
			//HORITZONTAL
			if(direccio<=5){
				//DRETA
				matriu[fila][(columna+3)]= charVaixell;
				matriu[fila][(columna+2)]= charVaixell;
				matriu[fila][(columna+1)]= charVaixell;
			}else{
				//ESQUERRA
				matriu[fila][(columna-3)]= charVaixell;
				matriu[fila][(columna-2)]= charVaixell;
				matriu[fila][(columna-1)]= charVaixell;
			}
		}else{
			//VERTICAL
			if(direccio<=5){
				//BAIX
				matriu[(fila+3)][columna]= charVaixell;
				matriu[(fila+2)][columna]= charVaixell;
				matriu[(fila+1)][columna]= charVaixell;
			}else{
				//DALT
				matriu[(fila-3)][columna]= charVaixell;
				matriu[(fila-2)][columna]= charVaixell;
				matriu[(fila-1)][columna]= charVaixell;
				
			}
		}
	}
	
	//FUNCIO QUE COLOCA ELS CUIRASSATS A LA MATRIU SEGONS DIRECCIO I SENTIT
	static void colocarCuirassats(char[][] matriu, int fila, int columna, char charVaixell, int sentit, int direccio, char jugador){
		Scanner teclat = new Scanner(System.in);
		char direccioChar;
		
		//SECUENCIA DE IF CLASIFICATS PER DIRECCIO I SENTIT   ---DRETA---
		if((sentit<=5) && (direccio<=5)){
			
			//FILTRE QUE MIRA SI EL VAIXELL COLOCAT, ESTA OCUPANT UNA POSICIO JA ASIGNADA A UN ALTRE VAIXELL
			if((filtrarCasella(matriu, fila, (columna+1))) || (filtrarCasella(matriu, fila, (columna+2)))){
				
				//SI ES JUGADOR FARA EL SEGUENT
				if (jugador=='s'){
					
					//TORNAR A DEMANAR DIRECCIO I SENTIT
					while((sentit<=5) && (direccio<=5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que el vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{ //SI ES MAQUINA FARA AIXO ALTRE
					while((sentit<=5) && (direccio<=5)){
						
						//TORNAR A GENERAR DIRECCIO I SENTIT
						sentit= random();
						direccio= random();
						
					}
				}
			}			
		}
		//SECUENCIA DE IF CLASIFICATS PER DIRECCIO I SENTIT   ---ESQUERRA---
		if((sentit<=5) && (direccio>5)){
			
			if((filtrarCasella(matriu, fila, (columna-1))) || (filtrarCasella(matriu, fila, (columna-2)))){
				
				if (jugador=='s'){
					
					while((sentit<=5) && (direccio>5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que el vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit<=5) && (direccio>5)){
						
						sentit= random();
						direccio= random();
					}
				}
			}
		}
		//SECUENCIA DE IF CLASIFICATS PER DIRECCIO I SENTIT   ---BAIX---
		if((sentit>5) && (direccio<=5)){
			
			if((filtrarCasella(matriu, fila+1, (columna))) || (filtrarCasella(matriu, fila+2, (columna)))){
				
				if (jugador=='s'){
					
					while((sentit>5) && (direccio<=5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que el vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit>5) && (direccio<=5)){
						
						sentit= random();
						direccio= random();
					}
				}
			}
		}
		//SECUENCIA DE IF CLASIFICATS PER DIRECCIO I SENTIT   ---DALT---
		if((sentit>5) && (direccio>5)){
			
			if((filtrarCasella(matriu, fila-1, (columna))) || (filtrarCasella(matriu, fila-2, (columna)))){
				
				if (jugador=='s'){
					
					while((sentit>5) && (direccio>5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que el vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit>5) && (direccio>5)){
						
						sentit= random();
						direccio= random();
					}	
				}
			}
		}
	
		//COLOCACIO DEL VAIXELL
		if(sentit<=5){
			//HORITZONTAL
			if(direccio<=5){
				//DRETA
				matriu[fila][(columna+2)]= charVaixell;
				matriu[fila][(columna+1)]= charVaixell;
			}else{
				//ESQUERRA
				matriu[fila][(columna-2)]= charVaixell;
				matriu[fila][(columna-1)]= charVaixell;
			}
		}else{
			//VERTICAL
			if(direccio<=5){
				//BAIX
				matriu[(fila+2)][columna]= charVaixell;
				matriu[(fila+1)][columna]= charVaixell;
			}else{
				//DALT
				matriu[(fila-2)][columna]= charVaixell;
				matriu[(fila-1)][columna]= charVaixell;
				
			}
		}
	}
	
	//FUNCIO QUE COLOCA LES FRAGATES A LA MATRIU SEGONS DIRECCIO I SENTIT
	static void colocarFragates(char[][] matriu, int fila, int columna, char charVaixell, int sentit, int direccio, char jugador){
		Scanner teclat = new Scanner(System.in);
		char direccioChar;
		
		if((sentit<=5) && (direccio<=5)){
			
			if((filtrarCasella(matriu, fila, columna+1))){
				
				if (jugador=='s'){
					
					while((sentit<=5) && (direccio<=5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que la vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit<=5) && (direccio<=5)){
						
						sentit= random();
						direccio= random();
						
					}
				}
			}			
		}
		if((sentit<=5) && (direccio>5)){
			
			if((filtrarCasella(matriu, fila, columna-1))){
				
				if (jugador=='s'){
					
					while((sentit<=5) && (direccio>5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que la vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit<=5) && (direccio>5)){
						
						sentit= random();
						direccio= random();
					}
				}
			}
		}
		if((sentit>5) && (direccio<=5)){
			
			if((filtrarCasella(matriu, fila+1, columna))){
				
				if (jugador=='s'){
					
					while((sentit>5) && (direccio<=5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que la vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit>5) && (direccio<=5)){
						
						sentit= random();
						direccio= random();
					}
				}
			}
		}
		if((sentit>5) && (direccio>5)){
			
			if((filtrarCasella(matriu, fila-1, columna))){
				
				if (jugador=='s'){
					
					while((sentit>5) && (direccio>5)){
						
						System.out.println("El vaixell choca amb un altre o has sortit del taulell de joc!");
						System.out.println("Introdueix la direccio en la que la vols colocar (W, A, S, D):");
						
						direccioChar= teclat.next().charAt(0);
						
						if(direccioChar == 'w'){
							
							sentit= 7;
							direccio= 7;
						}
						if(direccioChar == 'a'){
							
							sentit= 4;
							direccio= 7;
						}
						if(direccioChar == 's'){
							
							sentit= 7;
							direccio= 4;
						}
						if(direccioChar == 'd'){
							
							sentit= 4;
							direccio= 4;
						}
					}				
				}else{
					while((sentit>5) && (direccio>5)){
						
						sentit= random();
						direccio= random();
					}	
				}
			}
		}
		
		//COLOCACIO DEL VAIXELL
		if(sentit<=5){
			//HORITZONTAL
			if(direccio<=5){
				//DRETA
				matriu[fila][(columna+1)]= charVaixell;
			}else{
				//ESQUERRA
				matriu[fila][(columna-1)]= charVaixell;
			}
		}else{
			//VERTICAL
			if(direccio<=5){
				//BAIX
				matriu[(fila+1)][columna]= charVaixell;
			}else{
				//DALT
				matriu[(fila-1)][columna]= charVaixell;
				
			}
		}
	}
	
	
	
	/*     -------------------- FINAL COLOCACIO VAIXELLS JUGADOR --------------------     */
	
	/*     -------------------- INICI COLOCACIO VAIXELLS MAQUINA --------------------     */
	
	
	
	//FUNCIO CLASIFICA VAIXELL SEGONS LA LLARGADA QUE TINGUI
	static void posarVaixellMaquina (char[][] matriu, int vaixell){
		int llargada, cont= 1;
		
		if(vaixell == 1){
			
			llargada= 3;
			colocarVaixell(matriu, vaixell, llargada);
		}
		if(vaixell == 2){
			
			while(cont<=2){
				
				llargada= 2;
				colocarVaixell(matriu, vaixell, llargada);
				cont++;
			}
		}
		if(vaixell == 3){
			
			while(cont<=3){
				
				llargada= 1;
				colocarVaixell(matriu, vaixell, llargada);
				cont++;
			}
		}
		if(vaixell == 4){
			
			while(cont<=4){
				
				llargada= 0;
				colocarVaixell(matriu, vaixell, llargada);
				cont++;
			}
		}
	}
	
	//FUNCIO QUE COLOCA EL VAIXELL SEGONS LA DIRECCIO I LLARGADA QUE TINGUI
	static void colocarVaixell (char[][] matriu, int vaixell, int llargada){
		int fila, columna, sentit, direccio;
		char charVaixell= 'x';
		boolean casellaOcupada;
		
		//ASSIGNACIO DEL CARACTER CORRESPONENT A CADA UN DELS VAIXELLS
		switch (vaixell){
			case 1: charVaixell= 'A';
			break;
			case 2: charVaixell= 'C';
			break;
			case 3: charVaixell= 'F';
			break;
			case 4: charVaixell= 'P';
		}
		
		fila= random();
		columna= random();
		
		casellaOcupada= filtrarCasella(matriu, fila, columna);
		
		//FILTRE QUE ASSEGURA QUE LA POSICIO INICIAL DEL VAIXELL NO ES SOLAPI AMB CAP ALTRE VAIXELL
		// I QUE LA COLOCACIÓ D'AQUEST, NO SURTI MAI DEL TAULELL DE JOC
		while(((columna+llargada)>9) || ((columna-llargada)<0) || ((fila+llargada)>9) || ((fila-llargada)<0) || (casellaOcupada)){
			
			fila= random();
			columna= random();
			
			casellaOcupada= filtrarCasella(matriu, fila, columna);
		}
		
		//POSICIO INICIAL DEL VAIXELL
		matriu[fila][columna]= charVaixell;
		
		//SENTIT DEL VAIXELL
		sentit= random();
		//DIRECCIO DEL VAIXELL
		direccio= random();
		
		//ES CRIDA A LES FUNCIONS ANTERIORS PER TAL D'ESTALVIAR CODI I QUE COLOQUIN ELLES ELS VAIXELLS, SEGONS SI SON PORTAVIONS, CUIRASSATS O FRAGATES
		if(vaixell == 1){
			
			colocarPortavions(matriu, fila, columna, charVaixell, sentit, direccio);
		}
		if(vaixell == 2){
			
			colocarCuirassats(matriu, fila, columna, charVaixell, sentit, direccio, 'n');
		}
		if(vaixell == 3){
			
			colocarFragates(matriu, fila, columna, charVaixell, sentit, direccio, 'n');
		}
	}
	
	
	
	/*     -------------------- FINAL COLOCACIO VAIXELLS MAQUINA --------------------     */
	
	
	
	//FUNCIO QUE DEMANA LA DIRECCIO, ESQUERRA, DRETA, DALT, BAIX I RETORNA EL CARACTER CORRESPONENT
	static char demanarDireccio (){
		Scanner teclat = new Scanner(System.in);
		
		char dir;
		
		System.out.println("Introdueix la direccio en la que el vols colocar (W, A, S, D):");
		
		dir= teclat.next().charAt(0);
		
		while((dir != 'w') && (dir != 'a') && (dir != 's') && (dir != 'd')){
			
			System.out.println("Direccio introduida erronea, siusplau, torna a introduir:");
			dir= teclat.next().charAt(0);
		}
		
		return dir;
	}
	
	//FUNCIO QUE COMPROVA QUE LES DADES REBUDES PER PARAMETRE NO SURTIN DE TAULELL
	static boolean sortidaTaulell (char[][] matriu, int f, int  c){
		boolean fora= false;
		
		if ((c>9) || (c<0) || (f>9) || (f<0)){
			
			fora= true;
		}
		
		return fora;
	}
		
	//FUNCIO QUE RETORNA CERT O FALS, SEGONS SI LA POSICIO REBUDA ESTA OCUPANT UN LLOC JA ASSIGNAT PER ALGUN ALTRE VAIXELL O SURT DE RANG
	static boolean filtrarCasella (char[][] matriu, int f, int  c){
		boolean ocupada= false;
		
		if ((c>9) || (c<0) || (f>9) || (f<0)){
			
			ocupada= true;
		}else{
			if ((matriu[f][c]== 'A') || (matriu[f][c]== 'C') || (matriu[f][c]== 'F') || (matriu[f][c]== 'P')){
				
				ocupada= true;
			}else{
				
	//			if ((f==0) && (c==0)){
	//				
	//				if ((matriu[(f+1)][c]== 'A') || (matriu[(f+1)][c]== 'C') || (matriu[(f+1)][c]== 'F') || (matriu[(f+1)][c]== 'P')
	//					|| (matriu[f][(c+1)]== 'A') || (matriu[f][(c+1)]== 'C') || (matriu[f][(c+1)]== 'F') || (matriu[f][(c+1)]== 'P')){
	//						
	//					ocupada= true;
	//				}
	//			}
	//			if ((f==9) && (c==0)){
	//				
	//				if ((matriu[(f-1)][c]== 'A') || (matriu[(f-1)][c]== 'C') || (matriu[(f-1)][c]== 'F') || (matriu[(f-1)][c]== 'P')
	//					|| (matriu[f][(c+1)]== 'A') || (matriu[f][(c+1)]== 'C') || (matriu[f][(c+1)]== 'F') || (matriu[f][(c+1)]== 'P')){
	//						
	//					ocupada= true;
	//				}
	//			}
	//			if ((f==0) && (c==9)){
	//				
	//				if ((matriu[(f+1)][c]== 'A') || (matriu[(f+1)][c]== 'C') || (matriu[(f+1)][c]== 'F') || (matriu[(f+1)][c]== 'P')
	//					|| (matriu[f][(c-1)]== 'A') || (matriu[f][(c-1)]== 'C') || (matriu[f][(c-1)]== 'F') || (matriu[f][(c-1)]== 'P')){
	//						
	//					ocupada= true;
	//				}
	//			}
	//			if ((f==9) && (c==9)){
	//				
	//				if ((matriu[(f-1)][c]== 'A') || (matriu[(f-1)][c]== 'C') || (matriu[(f-1)][c]== 'F') || (matriu[(f-1)][c]== 'P')
	//					|| (matriu[f][(c-1)]== 'A') || (matriu[f][(c-1)]== 'C') || (matriu[f][(c-1)]== 'F') || (matriu[f][(c-1)]== 'P')){
	//						
	//					ocupada= true;
	//				}
	//			}
	//			
	//			
	//			
	//			if ((f==0) && (ocupada==false)){
	//				
	//				if ((matriu[(f+1)][c]== 'A') || (matriu[(f+1)][c]== 'C') || (matriu[(f+1)][c]== 'F') || (matriu[(f+1)][c]== 'P')
	//					|| (matriu[f][(c+1)]== 'A') || (matriu[f][(c+1)]== 'C') || (matriu[f][(c+1)]== 'F') || (matriu[f][(c+1)]== 'P')
	//					|| (matriu[f][(c-1)]== 'A') || (matriu[f][(c-1)]== 'C') || (matriu[f][(c-1)]== 'F') || (matriu[f][(c-1)]== 'P')){
	//						
	//					ocupada= true;
	//				}
	//			}
	//			if ((f==9) && (ocupada==false)){
	//							
	//				if ((matriu[(f-1)][c]== 'A') || (matriu[(f-1)][c]== 'C') || (matriu[(f-1)][c]== 'F') || (matriu[(f-1)][c]== 'P')
	//					|| (matriu[f][(c+1)]== 'A') || (matriu[f][(c+1)]== 'C') || (matriu[f][(c+1)]== 'F') || (matriu[f][(c+1)]== 'P')
	//					|| (matriu[f][(c-1)]== 'A') || (matriu[f][(c-1)]== 'C') || (matriu[f][(c-1)]== 'F') || (matriu[f][(c-1)]== 'P')){
	//					
	//					ocupada= true;
	//				}
	//			}
	//			if ((c==0) && (ocupada==false)){
	//				
	//				if ((matriu[(f+1)][c]== 'A') || (matriu[(f+1)][c]== 'C') || (matriu[(f+1)][c]== 'F') || (matriu[(f+1)][c]== 'P')
	//					|| (matriu[(f-1)][c]== 'A') || (matriu[(f-1)][c]== 'C') || (matriu[(f-1)][c]== 'F') || (matriu[(f-1)][c]== 'P')
	//					|| (matriu[f][(c+1)]== 'A') || (matriu[f][(c+1)]== 'C') || (matriu[f][(c+1)]== 'F') || (matriu[f][(c+1)]== 'P')){
	//					
	//					ocupada= true;
	//				}
	//			}
	//			if ((c==9) && (ocupada==false)){
	//				
	//				if ((matriu[(f+1)][c]== 'A') || (matriu[(f+1)][c]== 'C') || (matriu[(f+1)][c]== 'F') || (matriu[(f+1)][c]== 'P')
	//					|| (matriu[(f-1)][c]== 'A') || (matriu[(f-1)][c]== 'C') || (matriu[(f-1)][c]== 'F') || (matriu[(f-1)][c]== 'P')
	//					|| (matriu[f][(c-1)]== 'A') || (matriu[f][(c-1)]== 'C') || (matriu[f][(c-1)]== 'F') || (matriu[f][(c-1)]== 'P')){
	//					
	//					ocupada= true;
	//				}
	//			}
	//			
	//			
	//			
	//			if (ocupada= false){
	//				if ((matriu[(f+1)][c]== 'A') || (matriu[(f+1)][c]== 'C') || (matriu[(f+1)][c]== 'F') || (matriu[(f+1)][c]== 'P')
	//					|| (matriu[(f-1)][c]== 'A') || (matriu[(f-1)][c]== 'C') || (matriu[(f-1)][c]== 'F') || (matriu[(f-1)][c]== 'P')
	//					|| (matriu[f][(c+1)]== 'A') || (matriu[f][(c+1)]== 'C') || (matriu[f][(c+1)]== 'F') || (matriu[f][(c+1)]== 'P')
	//					|| (matriu[f][(c-1)]== 'A') || (matriu[f][(c-1)]== 'C') || (matriu[f][(c-1)]== 'F') || (matriu[f][(c-1)]== 'P')){
	//					
	//					ocupada= true;
	//				}
	//			}
			}
		}

		return ocupada;
	}
	
	//FUNCIO QUE EXECUTA LA TIRADA DE CASELLA I MOSTRA LA CASELLA DESTAPADA, TANT SI
	//HI HA AIGUA, COM VAIXELL, RETORNA UN BOOLEA DIENT SI HA ACERTAT EL VAIXELL
	static boolean tirada (char[][] matriuOculta, char[][] matriu, int fila, int columna){
		boolean acertat= false;
		
		matriuOculta[fila][columna]= matriu[fila][columna];
		
		if((matriu[fila][columna] == 'A') || (matriu[fila][columna] == 'C') || (matriu[fila][columna] == 'F') || (matriu[fila][columna] == 'P')){
			
			acertat= true;
		}
		
		return acertat;
	}
	
	//FUNCIO QUE COMPROBA QUE NO ES REPETEIXI LA TIRADA
	static boolean comprobarTirada (char[][] matriuOculta, int fila, int columna){
		boolean jaTocada= false;
			
		if((matriuOculta[fila][columna] == 'A') || (matriuOculta[fila][columna] == 'C') || (matriuOculta[fila][columna] == 'F') || (matriuOculta[fila][columna] == 'P')){
				
			jaTocada= true;
		}
			
		return jaTocada;
	}
	
	//FUNCIO QUE IMPRIMEIX LES INSTRUCCIONS DE LA COLOCACIO DE VAIXELLS
	static void instruccionsColocacio (){
		
		System.out.println();
		System.out.println("   ╔══════════════════╗");
		System.out.println("   ║   INSTRUCCIONS   ║");
		System.out.println("   ╚══════════════════╝");
		System.out.println();
		System.out.println("Introduir vaixell per vaixell indicant fila primer i columna despres.");
		System.out.println("Les files poden ser introduides tant en lletres com en numeros.");
		System.out.println("Un cop introduides fila i columna, indicar la direccio del vaixell:");
		System.out.println("W: Cap adalt.");
		System.out.println("A: Esquerra.");
		System.out.println("S: Cap abaix.");
		System.out.println("D: Dreta.");
		System.out.println();
	}
	
	//FUNCIO QUE IMPRIMEIX LES INSTRUCCIONS DE JUGAR
	static void instruccionsJugar (){
		
		System.out.println();
		System.out.println("   ╔══════════════════╗");
		System.out.println("   ║   INSTRUCCIONS   ║");
		System.out.println("   ╚══════════════════╝");
		System.out.println();
		System.out.println("Comença tirant el primer que ha omplert el taulell.");
		System.out.println("Si el jugador que esta tirant troba aigua, passa a tirar l'altre jugador.");
		System.out.println("Si el jugador que esta tirant troba vaixell, torna a tirar.");
		System.out.println();
		System.out.println("El jugador que toqui abans tots els vaixells, guanya.");
		System.out.println();
	}
	
	//FUNCIO QUE DEMANA CONTINUAR, SI S'INTRODUEIX 'S', CONTINUA
	static void continuar (){
		Scanner teclat = new Scanner(System.in);
		
		char acceptar;
		
		System.out.println("Continuar? s/n");
		acceptar= teclat.next().charAt(0);
		
		while(acceptar != 's'){
			
			System.out.println("Continuar? s/n");
			acceptar= teclat.next().charAt(0);
		}
		cls();
	}
	
	//FUNCIO QUE TRANSFORMA LES FILES DE CARACTERS A ENTERS
	static int transformar (char filaCaracter){
		int fila= 0;
		
		switch (filaCaracter){
		
		case 'A':
		case 'a':
			fila= 0;
		break;
		case 'B':
		case 'b':
			fila= 1;
		break;
		case 'C':
		case 'c':
			fila= 2;
		break;
		case 'D':
		case 'd':
			fila= 3;
		break;
		case 'E':
		case 'e':
			fila= 4;
		break;
		case 'F':
		case 'f':
			fila= 5;
		break;
		case 'G':
		case 'g':
			fila= 6;
		break;
		case 'H':
		case 'h':
			fila= 7;
		break;
		case 'I':
		case 'i':
			fila= 8;
		break;
		case 'J':
		case 'j':
			fila= 9;
		break;
		}
		
		return fila;
	}
	
	//FUNCIO QUE TRANSFORMA LES FILES DE NUMEROS A CARACTERS
	static char transformarInv (int fila){
		char filaChar= 'x';
			
		switch (fila){
		
		case 0:
			filaChar= 'A';
		break;
		case 1:
			filaChar= 'B';
		break;
		case 2:
			filaChar= 'C';
		break;
		case 3:
			filaChar= 'D';
		break;
		case 4:
			filaChar= 'E';
		break;
		case 5:
			filaChar= 'F';
		break;
		case 6:
			filaChar= 'G';
		break;
		case 7:
			filaChar= 'H';
		break;
		case 8:
			filaChar= 'I';
		break;
		case 9:
			filaChar= 'J';
		break;
		}
		
		return filaChar;
	}
	
	//FUNCIO QUE NETEJA EL TERMINAL
	static void cls (){
		
		for(int i=0; i<2; i++){
			
			System.out.println();
		}
	}
}