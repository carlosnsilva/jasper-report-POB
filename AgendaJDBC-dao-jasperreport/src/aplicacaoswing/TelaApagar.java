package aplicacaoswing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import fachada.Fachada;

public class TelaApagar {
	private JFrame frame;
	private JLabel label_2;
	private JTextField textField;
	private JButton button;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private final ButtonGroup grupo = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaCadastrar window = new TelaCadastrar();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaApagar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.setTitle("Apagar");
		frame.setBounds(100, 100, 263, 211);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label_2 = new JLabel("");
		label_2.setBounds(10, 147, 227, 14);
		frame.getContentPane().add(label_2);

		textField = new JTextField();
		textField.setBounds(63, 50, 121, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		button = new JButton("Apagar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String termo = textField.getText();
					if(radioButton.isSelected())
						Fachada.excluirPessoa(termo);
					else
						Fachada.excluirTelefone(termo);
					
					textField.setText("");
					label_2.setText("exclusão realizada");
				}
				catch(Exception e) {
					label_2.setText(e.getMessage());
				}
			}
		});
		button.setBounds(69, 94, 108, 23);
		frame.getContentPane().add(button);

		radioButton = new JRadioButton("pessoa");
		radioButton.setSelected(true);
		grupo.add(radioButton);
		radioButton.setBounds(28, 20, 84, 23);
		frame.getContentPane().add(radioButton);

		radioButton_1 = new JRadioButton("telefone");
		grupo.add(radioButton_1);
		radioButton_1.setBounds(119, 20, 109, 23);
		frame.getContentPane().add(radioButton_1);

		frame.setVisible(true);
	}
}
