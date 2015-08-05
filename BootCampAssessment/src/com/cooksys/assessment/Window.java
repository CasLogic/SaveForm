package com.cooksys.assessment;

import java.awt.*;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

@XmlRootElement
public class Window {
	//array list for parts and the index of those parts
	ArrayList<Object> j = new ArrayList<Object>();
	int index = 0;
	private JFrame frmPcPartBuilder;

	/**
	 * Launch the application. The main method is the entry point to a Java application. 
	 * For this assessment, you shouldn't have to add anything to this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmPcPartBuilder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder
	 * will generate its code.
	 */
	public void initialize() {
		frmPcPartBuilder = new JFrame();
		frmPcPartBuilder.setTitle("PC Part Builder");
		frmPcPartBuilder.setBounds(100, 100, 450, 300);
		frmPcPartBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmPcPartBuilder.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		//adds menu file to menu bar
		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		
		//creates action for load menu item
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//calls load function
				load();
			}
		});
		//adds load to menu file
		mnFile.add(mntmLoad);
		
		//creates action for save menu item
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//calls save function
				save();
			}
		});
		//adds save to menu file
		mnFile.add(mntmSave);
		
		//creates action for exit menu item
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//disposes entire form
				frmPcPartBuilder.dispose();;
			}
		});
		//adds exit to menu file
		mnFile.add(mntmExit);
		//sets layout
		frmPcPartBuilder.getContentPane().setLayout(new BorderLayout(0, 0));
		//adds panel to center
		JPanel panelCenter = new JPanel();
		frmPcPartBuilder.getContentPane().add(panelCenter);
		panelCenter.setLayout(null);
		//adds buttons to center panel
		JButton btnRemove = new JButton("<<");
		btnRemove.setBounds(175, 58, 49, 23);
		panelCenter.add(btnRemove);
		JButton btnForward = new JButton(">>");
		btnForward.setBounds(175, 104, 49, 23);
		panelCenter.add(btnForward);
		//adds panel to west section
		JPanel panelWest = new JPanel();
		frmPcPartBuilder.getContentPane().add(panelWest, BorderLayout.WEST);
		//adds parts to parts list
		final DefaultListModel parts = new DefaultListModel();
		
		parts.addElement("Case");
		parts.addElement("Motherboard");
		parts.addElement("CPU");
		parts.addElement("PSU");
		parts.addElement("RAM");
		parts.addElement("HDD");
		
		//adds parts list to list west
		final JList listWest = new JList(parts);
		listWest.setValueIsAdjusting(true);
		listWest.setVisibleRowCount(15);
		
		//adds list west to west panel
		panelWest.add(listWest);
		
		//adds panel to east section
		JPanel panelEast = new JPanel();
		frmPcPartBuilder.getContentPane().add(panelEast, BorderLayout.EAST);
		
		final DefaultListModel parts1 = new DefaultListModel();
		
		//adds list east to east panel
		final JList listEast = new JList(parts1);
		panelEast.add(listEast);
		
		//creates action for forward button
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object data = "";
				//if list isnt empty move selected item from west panel to east panel
				if(listWest.getSelectedIndex()!=-1){
					data = listWest.getSelectedValue();
					parts.removeElement(data);
					parts1.addElement(data);
					//adds part to array list
					j.add(index,data);
					index++;
				}
			}
		});
		//creates action for remove button
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object data = "";
				//if list isnt empty move selected item from east panel to west panel
				if(listEast.getSelectedIndex()!=-1){
					data = listEast.getSelectedValue();
					parts1.removeElement(data);
					parts.addElement(data);
					//removes part to array list
					j.remove(data);
					index--;
				}
			}
		});		
	}
	//sets array list
	private void setPart(ArrayList<Object> parts1) {
		j = parts1;		
	}
	//gets array list 
	@XmlElement
	private ArrayList<Object> getPart() {
		return j;
	}
	//saves parts from West List to NewFile.xml 
	public void save(){
		Window window = new Window();
		//sets window part to parts in west list
		window.setPart(j);
		try{
		File file = new File("src/data/NewFile.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Window.class);
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		jaxbMarshaller.marshal(window, System.out);
		jaxbMarshaller.marshal(window, file);
		
		}  
		catch (JAXBException e) {
			e.printStackTrace();
	      } 
	}
 
	//loads file and gets the parts from West List
	public void load(){
		try {
			 
			File file = new File("src/data/NewFile.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Window.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Window w = (Window) jaxbUnmarshaller.unmarshal(file);
			System.out.println("West List");
			System.out.println(w.getPart());
			
		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
	}
}
