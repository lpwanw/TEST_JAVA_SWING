/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Quanli;

import GUI.HienThi.ThemSuaNhanVien;
import GUI.HienThi.HienThiNhanVien;
import BUS.NhanVienBUS;
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import GUI.Button.*;
import GUI.*;
import GUI.Excel.DocExcel;
import GUI.Excel.XuatExcel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author phuon
 */
public class QuanliNhanVienForm extends JPanel {

    HienThiNhanVien formHienThi = new HienThiNhanVien();
    ThemButton btnThem = new ThemButton();
    SuaButton btnSua = new SuaButton();
    XoaButton btnXoa = new XoaButton();
    //NhanVienDTO nvSua = new NhanVienDTO();
    ExportExcelButton btnXuatExcel = new ExportExcelButton();
    ImportExcelButton btnNhapExcel = new ImportExcelButton();

    public QuanliNhanVienForm() {
        setLayout(new BorderLayout());

        // buttons
        if (!DangNhap.quyenLogin.getChitiet().contains("qlNhanVien")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
            btnNhapExcel.setEnabled(false);
            btnXuatExcel.setEnabled(false);
            formHienThi.getTable().getTable().setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnXuatExcel);
        plBtn.add(btnNhapExcel);

        this.add(plBtn, BorderLayout.NORTH);
        this.add(formHienThi, BorderLayout.CENTER);
        //action Listener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
        });
        btnXuatExcel.addActionListener((ActionEvent ae) -> {
            new XuatExcel().xuatFileExcelNhanVien();
        });
        btnNhapExcel.addActionListener((ActionEvent ae) -> {
            new DocExcel().docFileExcelNhanVien();
        });
    }

    private void btnThemMouseClicked() {
        ThemSuaNhanVien themnv = new ThemSuaNhanVien("Th??m", "");
        themnv.setVisible(true);
        themnv.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }

    private void btnXoaMouseClicked() {
        if (formHienThi.getTable().getTable().getSelectedRow()>=0) {
            NhanVienDTO nv = new NhanVienBUS().getNV(formHienThi.getSelectedRow(1));
            if (nv.getTrangThai() == 1) {
                int reply = JOptionPane.showConfirmDialog(formHienThi, "B???n c?? mu???n ???n nh??n vi??n?????????");
                if (reply == JOptionPane.YES_OPTION) {
                    nv.setTrangThai(0);
                    NhanVienDAO.updateNhanVien(nv);
                    JOptionPane.showMessageDialog(formHienThi, "???n nh??n vi??n th??nh c??ng");
                    formHienThi.refresh();
                } else {
                    JOptionPane.showMessageDialog(formHienThi, "???n nh??n vi??n kh??ng th??nh c??ng");
                }
            } else {
                int reply = JOptionPane.showConfirmDialog(formHienThi, "B???n c?? mu???n x??a nh??n vi??n?????????");
                if (reply == JOptionPane.YES_OPTION) {
                    if(NhanVienDAO.DeleteNhanVien(nv)){;
                        JOptionPane.showMessageDialog(formHienThi, "x??a nh??n vi??n th??nh c??ng");
                    }else{
                        JOptionPane.showMessageDialog(formHienThi, "x??a nh??n vi??n kh??ng th??nh c??ng");
                    }
                    formHienThi.refresh();
                } else {
                    JOptionPane.showMessageDialog(formHienThi, "x??a nh??n vi??n kh??ng th??nh c??ng");
                }
            }
        }else{
            JOptionPane.showMessageDialog(formHienThi,"B???n ph???i ch???n 1 nh??n vi??n ????? c?? th??? x??a");
        }
    }

    private void btnSuaMouseClicked() {
        ThemSuaNhanVien themnv = new ThemSuaNhanVien("S???a", formHienThi.getSelectedRow(1));
        themnv.setVisible(true);
        themnv.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
