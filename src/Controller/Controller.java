package Controller;

import java.util.ArrayList;
import java.util.LinkedList;

import FileReader.FileReaderTXT;
import machine.TuringMachine;
import machine.TuringState;

public class Controller {

	TuringMachine machine;
	FileReaderTXT fileReader;
	ArrayList<LinkedList<String>> oldTapeStates;
	ArrayList<TuringState> oldStates;
	ArrayList<Integer> oldHead;
	private static final String newLine = System.getProperty("line.separator").toString();

	public Controller(){
		oldTapeStates = new ArrayList<>();
		oldStates = new ArrayList<>();
		oldHead = new ArrayList<>();
	}

	public void readFile(String fileName) throws Exception {
		fileReader = new FileReaderTXT(fileName);
	}

	public void createMachineFromFile() throws Exception {
		this.machine = fileReader.getMachine();
	}

	public void createMachineFromFile(int starter) throws Exception {
		this.machine = fileReader.getMachine(starter);
	}



	public void machineRun() throws Exception {
		while (!machine.getCurrentState().isAcceptanceState() && !machine.getCurrentState().isGarbageState()){
			machineNextStep();
		}
	}

	public void machineNextStep() throws Exception{
		saveState();
		machine.nextStep();
		printMachine();
	}

	/**
	 * Volta um passo na execução da máquina.
	 */
	public void undo(){
		machine.setHeadPosition(oldHead.get(oldHead.size() - 1));
		oldHead.remove(oldHead.size() - 1);

		machine.setState(oldStates.get(oldStates.size() - 1));
		oldStates.remove(oldStates.size() - 1);

		machine.setInitialInput(oldTapeStates.get(oldTapeStates.size() - 1));
		oldTapeStates.remove(oldTapeStates.size() - 1);
		
		printMachine();
	}

	public void machineReset() {
		machine.reset();
	}

	public int getMachineHeadPosition() {
		return machine.getHeadPosition();
	}

	public TuringState getCurrentMachineState() {
		return machine.getCurrentState();
	}

	public String getMachineTape() {
		return machine.getTape().toString();
	}

	public ArrayList<LinkedList<String>> getOldTapeStates(){
		return this.oldTapeStates;
	}

	public ArrayList<Integer> getOldHead(){
		return this.oldHead;
	}

	public ArrayList<TuringState> getOldStates(){
		return this.getOldStates();
	}

	public LinkedList<String> createFinalInput(String input){
		LinkedList<String> finalInput = new LinkedList<>();
		for (int i = 0; i < input.length(); i++){
			if (!input.substring(i, i + 1).equals(" "))
				finalInput.add(input.substring(i, i + 1));
			else if (input.substring(i, i + 1).equals("*"))
				machine.setHeadPosition(i);
			else
				finalInput.add("_");
		}
		return finalInput;
	}

	public void setMachineInitialInput(String initialInput) {
		LinkedList<String> finalInitialInput = createFinalInput(initialInput);
		machine.setInitialInput(finalInitialInput);

		machine.setHeadPosition(headPosition(initialInput));
	}

	private int headPosition(String initialInput) {
		if (initialInput.indexOf("*") != -1)
			return initialInput.indexOf("*") + 1;

		else
			return 0;
	}

	public boolean isAcceptanceState(){
		return this.machine.isAcceptanceState();
	}

	public boolean isGarbageState(){
		return this.machine.isGarbageState();
	}

	public void saveState() {
		oldHead.add(machine.getHeadPosition());
		oldStates.add(machine.getCurrentState());
		oldTapeStates.add(machine.getTape());
	}

	public void printMachine() {
		StringBuilder sb = new StringBuilder();

		// estado atual, fita , posicao do cabecote
		sb.append("Estado atual: " + machine.getCurrentState().toString() + newLine);
		sb.append(machine.getTape().toString() + newLine);
		for(int i = 0; i < machine.getHeadPosition(); i++) {
			sb.append("   ");
		}
		sb.append(" ^" +newLine);

		System.out.println(sb.toString());
	}
}
