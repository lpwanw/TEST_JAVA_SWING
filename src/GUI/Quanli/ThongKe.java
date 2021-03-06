/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// them 2 form chuyen nghiep
package GUI.Quanli;

import BUS.*;
import BUS.Tool;
import DAO.CTPhieuNhapDAO;
import DAO.ThongKeDAO;
import DTO.CTHoaDonDTO;
import DTO.CTPhieuNhapDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import GUI.Button.DateButton;
import GUI.Button.RefreshButton;
import GUI.HienThi.FormHienThi;
import GUI.MyTable;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JFrame;

/**
 *
 * @author phuon
 */
public class ThongKe extends JPanel {
    public static File fontFile = new File("src/font/vuTimes.ttf");
    BaseFont bf;
    com.itextpdf.text.Font font;
    public static String stTong;
    public static String stBan;
    public static String stNhap;
    HoaDonBUS qlHD = new HoaDonBUS();
    NhanVienBUS qlNV = new NhanVienBUS();
    KhachHangBUS qlKH = new KhachHangBUS();
    NhaCungCapBUS qlNCC = new NhaCungCapBUS();
    SanPhamBUS qlSP = new SanPhamBUS();
    PhieuNhapBUS qlPN = new PhieuNhapBUS();
    CTPhieuNhapBUS qlCTPN = new CTPhieuNhapBUS();
    CTHDBUS qlCTHD = new CTHDBUS();
    JButton btnThongKetatca = new JButton("Th???ng k?? t???t c???");
    thongKeTongQuat tkTQ = new thongKeTongQuat();
    thongKeSanPham tkSP = new thongKeSanPham();

    public ThongKe() throws DocumentException, IOException {
        this.bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        font = new com.itextpdf.text.Font(bf);
        font.setSize(15);
        setLayout(new BorderLayout());
        JPanel plButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plButton.add(btnThongKetatca);
        btnThongKetatca.setIcon(new ImageIcon("src/Image/bar_chart_50px.png"));
        JPanel plCenter = new JPanel();
        add(plButton, BorderLayout.NORTH);
        add(plCenter, BorderLayout.CENTER);
        //action
        plCenter.add(tkTQ);
        btnThongKetatca.addActionListener((e) -> {
            plCenter.removeAll();
            plCenter.add(tkTQ);
            revalidate();
            repaint();
        });
    }

    public class thongKeSanPham extends JPanel {

        JComboBox<String> cbNam = new JComboBox<>();
        JComboBox<String> cbTypeSearch = new JComboBox<>();
        JComboBox<String> cbValue = new JComboBox<>();
        MyTable tbBan = new MyTable();
        MyTable tbNhap = new MyTable();

        public thongKeSanPham() {
            //menu.setPreferredSize(new Dimension(1920 - 300, 750));
            setPreferredSize(new Dimension(1920 - 300, 800));
            setBackground(Color.DARK_GRAY);
            setLayout(new BorderLayout());
            JPanel plHeader = new JPanel(new BorderLayout());
            JPanel plheadLeft = new JPanel();
            JPanel plheadvalue = new JPanel(new FlowLayout(FlowLayout.LEFT));
            plHeader.add(plheadLeft, BorderLayout.WEST);
            plHeader.add(plheadvalue, BorderLayout.CENTER);
            for (int i = 2010; i <= LocalDate.now().getYear(); i++) {
                cbNam.addItem(i + "");
            }
            cbNam.setBorder(BorderFactory.createTitledBorder("Ch???n n??m"));
            cbTypeSearch.setBorder(BorderFactory.createTitledBorder("Th???ng k?? theo"));
            cbValue.setBorder(BorderFactory.createTitledBorder("Th??ng"));
            cbNam.setPreferredSize(new Dimension(100, 60));
            cbTypeSearch.setPreferredSize(new Dimension(100, 60));
            cbValue.setPreferredSize(new Dimension(100, 60));
            plheadLeft.add(cbNam);
            plheadLeft.add(cbTypeSearch);
            cbTypeSearch.addItem("N??m");
            cbTypeSearch.addItem("Qu??");
            cbTypeSearch.addItem("Th??ng");
            JPanel work = new JPanel();
            work.setLayout(new BorderLayout());
            tbBan.setHeaders(new String[]{
                "M?? s???n ph???m",
                "T??n s???n ph???m",
                "S??? l?????ng"
            });
            tbNhap.setHeaders(new String[]{
                "M?? s???n ph???m",
                "T??n s???n ph???m",
                "S??? l?????ng"});
            work.add(tbBan, BorderLayout.NORTH);
            work.add(tbNhap, BorderLayout.SOUTH);
            String dateb = (String) cbNam.getSelectedItem() + "-01-01";
            String datee = (String) cbNam.getSelectedItem() + "-12-31";
            cbNam.addItemListener((e) -> {
                String dateb1 = (String) cbNam.getSelectedItem() + "-01-01";
                String datee1 = (String) cbNam.getSelectedItem() + "-12-31";
                setdatatoTable(dateb1, datee1);
            });
            setdatatoTable(dateb, datee);
            cbTypeSearch.addItemListener((e) -> {
                String type = (String) cbTypeSearch.getSelectedItem();
                plheadvalue.removeAll();
                cbValue.removeAllItems();
                switch (type) {
                    case "N??m": {
                        String date1 = (String) cbNam.getSelectedItem() + "-01-01";
                        String date2 = (String) cbNam.getSelectedItem() + "-12-31";
                        setdatatoTable(date1, date2);
                        break;
                    }
                    case "Qu??": {
                        cbValue.addItem("1");
                        cbValue.addItem("2");
                        cbValue.addItem("3");
                        cbValue.addItem("4");
                        plheadvalue.add(cbValue);
                        cbValue.setBorder(BorderFactory.createTitledBorder("Qu??"));
                        break;
                    }
                    case "Th??ng": {
                        cbValue.addItem("1");
                        cbValue.addItem("2");
                        cbValue.addItem("3");
                        cbValue.addItem("4");
                        cbValue.addItem("5");
                        cbValue.addItem("6");
                        cbValue.addItem("7");
                        cbValue.addItem("8");
                        cbValue.addItem("9");
                        cbValue.addItem("10");
                        cbValue.addItem("11");
                        cbValue.addItem("12");
                        plheadvalue.add(cbValue);
                        cbValue.setBorder(BorderFactory.createTitledBorder("Th??ng"));
                        break;
                    }
                }
                revalidate();
                repaint();
            });
            work.setBackground(Color.DARK_GRAY);
            this.add(plHeader, BorderLayout.NORTH);
            this.add(work, BorderLayout.CENTER);
        }

        public void setdatatoTable(String date1, String date2) {
            tbBan.clear();
            tbNhap.clear();
            String sp1 = "";
            String sp2 = "";
            ArrayList<String> masp = new ArrayList<>();
            int[] soluong = new int[99999];
            ArrayList<String> maspNhap = new ArrayList<>();
            int[] soluongNhap = new int[99999];
            int dem1 = 0;
            int dem2 = 0;
            ArrayList<HoaDonDTO> list = qlHD.Search("", "T???t c???", Tool.getDate(date1).toLocalDate(), Tool.getDate(date2).toLocalDate(), 0, 0);
            ArrayList<PhieuNhapDTO> list1 = qlPN.Search("", "T???t c???", Tool.getDate(date1).toLocalDate(), Tool.getDate(date2).toLocalDate(), 0, 0);
            for (HoaDonDTO hd : list) {
                ArrayList<CTHoaDonDTO> listCTHD = qlCTHD.Seacrh(hd.getMaHoaDon());
                for (CTHoaDonDTO cthd : listCTHD) {
                    if (masp.size() > 0) {
                        if (sp1.contains(cthd.getMaSanPham())) {
                            for (int i = 0; i < masp.size(); i++) {
                                if (masp.get(i).equals(cthd.getMaSanPham())) {
                                    soluong[i] += cthd.getSoLuong();
                                    break;
                                }
                            }
                        } else {
                            sp1 += cthd.getMaSanPham();
                            masp.add(cthd.getMaSanPham());
                            soluong[dem1++] = 0;
                        }
                    } else {
                        sp1 = cthd.getMaSanPham();
                        masp.add(cthd.getMaSanPham());
                        soluong[dem1++] = 0;
                    }

                }
            }
            for (PhieuNhapDTO pn : list1) {
                ArrayList<CTPhieuNhapDTO> listCTPN = qlCTPN.Seacrh(pn.getMaPhieuNhap());
                for (CTPhieuNhapDTO ctpn : listCTPN) {
                    if (maspNhap.size() > 0) {
                        if (sp2.contains(ctpn.getMaSanPham())) {
                            for (int i = 0; i < maspNhap.size(); i++) {
                                if (maspNhap.get(i).equals(ctpn.getMaSanPham())) {
                                    soluongNhap[i] += ctpn.getSoLuong();
                                }
                            }
                        } else {
                            sp2 += ctpn.getMaSanPham();
                            maspNhap.add(ctpn.getMaSanPham());
                            soluong[maspNhap.indexOf(ctpn.getMaSanPham())] = 0;
                        }
                    } else {
                        sp2 += ctpn.getMaSanPham();
                        maspNhap.add(ctpn.getMaSanPham());
                        soluong[maspNhap.indexOf(ctpn.getMaSanPham())] = 0;
                    }
                }
            }
            for (String k : masp) {
                tbBan.addRow(new String[]{
                    k,
                    qlSP.getSanPham(k).getTenSanPham(),
                    soluongNhap[masp.indexOf(k)] + ""
                });
            }
            for (String k : maspNhap) {
                tbNhap.addRow(new String[]{
                    k,
                    qlSP.getSanPham(k).getTenSanPham(),
                    soluongNhap[maspNhap.indexOf(k)] + ""
                });
            }
        }

        public JPanel TongSo(String type, int sl) {
            JPanel panel = new JPanel(null);
            Font font = new Font("Segui", 0, 25);
            Font font1 = new Font("Segui", 0, 30);
            panel.setSize(350, 300);
            panel.setPreferredSize(new Dimension(350, 300));
            //panel.setBackground(Color.DARK_GRAY);
            JLabel image = new JLabel();
            JLabel title = new JLabel();
            JLabel lbsl = new JLabel();
            image.setBounds(30, 30, 100, 150);
            title.setBounds(150, 50, 150, 50);
            title.setFont(font);
            lbsl.setBounds(200, 150, 100, 50);
            lbsl.setFont(font1);
            if (type.equals("S???n ph???m nh???p")) {
                Tool.setPicture(image, "product_100px.png");
                title.setText("S???n ph???m nh???p");
                lbsl.setText(sl + "");
            } else if (type.equals("S???n ph???m b??n")) {
                Tool.setPicture(image, "product_100px.png");
                title.setText("S???n ph???m b??n");
                lbsl.setText(sl + "");
            } else if (type.equals("Kh??ch h??ng")) {
                Tool.setPicture(image, "account_100px.png");
                title.setText("Kh??ch h??ng");
                lbsl.setText(sl + "");
            } else if (type.equals("Nh?? cung c???p")) {
                Tool.setPicture(image, "company_50px.png");
                title.setText("Nh?? cung c???p");
                lbsl.setText(sl + "");
            } else {
                Tool.setPicture(image, "money_100px.png");
                title.setText(type);
                lbsl.setText(Tool.getMonney(sl) + ",000 vn??");
                lbsl.setBounds(10, 200, 300, 40);
            }
            panel.add(image);
            panel.add(lbsl);
            panel.add(title);
            panel.setBorder(BorderFactory.createBevelBorder(1));
            return panel;
        }

    }

    public class thongKeTongQuat extends JPanel {

        RefreshButton btnRefresh = new RefreshButton();
        JTextField txKhoangNgay1 = new JTextField(8);
        JTextField txKhoangNgay2 = new JTextField(8);
        DatePicker dPicker1;
        DatePicker dPicker2;
        JTextField txNhanVien = new JTextField(8);
        JTextField txSanPham = new JTextField(8);
        JTextField txNhaCungCap = new JTextField(8);
        workspace wp = new workspace();
        JPanel pltimKiemSanPham = new JPanel();
        JPanel pltimKiemNhanVien = new JPanel();
        JPanel pltimKiemNhaCungCap = new JPanel();

        public thongKeTongQuat() {
            setLayout(new BorderLayout());
            //khoang ngay
            DatePickerSettings pickerSettings = new DatePickerSettings();
            pickerSettings.setVisibleDateTextField(false);
            dPicker1 = new DatePicker(pickerSettings);
            dPicker2 = new DatePicker(pickerSettings.copySettings());
            dPicker1.setDateToToday();
            dPicker2.setDateToToday();
            //button
            DateButton db = new DateButton(dPicker1);
            DateButton db2 = new DateButton(dPicker2);
            txKhoangNgay1.setBorder(BorderFactory.createTitledBorder("T???:"));
            txKhoangNgay2.setBorder(BorderFactory.createTitledBorder("?????n:"));
            dPicker1.addDateChangeListener((dce) -> {
                txKhoangNgay1.setText(dPicker1.getDateStringOrEmptyString());
            });
            dPicker2.addDateChangeListener((dce) -> {
                txKhoangNgay2.setText(dPicker2.getDateStringOrEmptyString());
            });
            JPanel plHeader = new JPanel();
            JPanel plTimKiemKhoangNgay = new JPanel();
            plTimKiemKhoangNgay.setBorder(BorderFactory.createTitledBorder("Ng??y l???p:"));
            plTimKiemKhoangNgay.add(txKhoangNgay1);
            plTimKiemKhoangNgay.add(dPicker1);
            plTimKiemKhoangNgay.add(txKhoangNgay2);
            plTimKiemKhoangNgay.add(dPicker2);
            plHeader.add(plTimKiemKhoangNgay);
            pltimKiemSanPham.setBorder(BorderFactory.createTitledBorder("S???n ph???m"));
            pltimKiemNhanVien.setBorder(BorderFactory.createTitledBorder("Nh??n vi??n"));
            pltimKiemNhaCungCap.setBorder(BorderFactory.createTitledBorder("Nh?? cung c???p"));
            pltimKiemSanPham.add(txSanPham);
            pltimKiemNhanVien.add(txNhanVien);
            pltimKiemNhaCungCap.add(txNhaCungCap);
            JButton xuatPDF = new JButton("Xu???t PDF");
            xuatPDF.setIcon(new ImageIcon("src/Image/pdf_50px.png"));
            plHeader.add(pltimKiemSanPham);
            plHeader.add(pltimKiemNhanVien);
            plHeader.add(pltimKiemNhaCungCap);
            plHeader.add(btnRefresh);
            plHeader.add(xuatPDF);
            this.add(plHeader, BorderLayout.NORTH);
            this.add(wp, BorderLayout.CENTER);
            DocumentListenner(txSanPham);
            DocumentListenner(txKhoangNgay1);
            DocumentListenner(txKhoangNgay2);
            DocumentListenner(txNhaCungCap);
            DocumentListenner(txNhanVien);
            btnRefresh.addActionListener((e) -> {
                refresh();
            });
            xuatPDF.addActionListener((ActionEvent e) -> {
                try {
                    xuatpdf();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        public void xuatpdf() throws FileNotFoundException, DocumentException {
            Document document = new Document();
            FileDialog fd = new FileDialog(new JFrame(), "Xu???t PDF", FileDialog.SAVE);
            fd.setFile("untitla.pdf");
            fd.setVisible(true);
            String url = fd.getDirectory() + fd.getFile();

            try {
                // kh???i t???o m???t PdfWriter truy???n v??o document v?? FileOutputStream
                PdfWriter.getInstance(document, new FileOutputStream(url));
                // m??? file ????? th???c hi???n vi???t
                document.open();
                // th??m n???i dung s??? d???ng add function
                if (wp.menu.getSelectedIndex() == 0) {
                    xuatTong(document);
                    PrintPS a = new PrintPS(document);
                }
                if (wp.menu.getSelectedIndex() == 1) {
                    XuatBan(document);
                    PrintPS a = new PrintPS(document);
                }
                if (wp.menu.getSelectedIndex() == 2) {
                    XuatNhap(document);
                    PrintPS a = new PrintPS(document);
                }
                // ????ng file
                document.close();

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void xuatTong(Document d) throws DocumentException {
            Paragraph a = new Paragraph(stTong,font);
            d.add(a);
        }
        public void XuatNhap(Document d) throws DocumentException{
            Paragraph a = new Paragraph(stNhap,font);
            d.add(a);
        }
        public void XuatBan(Document d) throws DocumentException{
            Paragraph a = new Paragraph(stBan,font);
            d.add(a);
        }
        private void refresh() {
            txKhoangNgay1.setText("");
            txKhoangNgay2.setText("");
            txNhaCungCap.setText("");
            txNhanVien.setText("");
            txSanPham.setText("");
            qlCTHD.getData();
            qlCTPN.getData();
            if (wp.menu.getSelectedComponent().equals(wp.banra)) {
                wp.banra.setDatatoTable(qlCTHD.list);
            }
            if (wp.menu.getSelectedComponent().equals(wp.nhaphang)) {
                wp.nhaphang.setDatatoTable(qlCTPN.list);
            }
        }

        private void DocumentListenner(JTextField tx) {
            tx.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    txSearchOnChange();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    txSearchOnChange();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    txSearchOnChange();
                }
            });
        }

        public void txSearchOnChange() {
            LocalDate ngay1 = null, ngay2 = null;
            try {
                ngay1 = java.time.LocalDate.parse(txKhoangNgay1.getText());
                txKhoangNgay1.setForeground(Color.black);
            } catch (DateTimeParseException e) {
                txKhoangNgay1.setForeground(Color.red);
            }
            try {
                ngay2 = java.time.LocalDate.parse(txKhoangNgay2.getText());
                txKhoangNgay2.setForeground(Color.black);
            } catch (DateTimeParseException e) {
                txKhoangNgay2.setForeground(Color.red);
            }
            if (txKhoangNgay1.getText().equals("") && txKhoangNgay2.getText().equals("") && txNhaCungCap.getText().equals("") && txNhanVien.getText().equals("") && txSanPham.getText().equals("")) {
                if (wp.menu.getSelectedComponent().equals(wp.banra)) {
                    wp.banra.setDatatoTable(qlCTHD.list);
                }
                if (wp.menu.getSelectedComponent().equals(wp.nhaphang)) {
                    wp.nhaphang.setDatatoTable(qlCTPN.list);
                }
                return;
            }
            if (wp.menu.getSelectedComponent().equals(wp.banra)) {
                wp.banra.setDatatoTable(getCTHOADON(ngay1, ngay2, txSanPham.getText(), txNhanVien.getText(), txNhaCungCap.getText()));
            }
            if (wp.menu.getSelectedComponent().equals(wp.nhaphang)) {
                wp.nhaphang.setDatatoTable(getCTPhieuNhap(ngay1, ngay2, txSanPham.getText(), txNhanVien.getText(), txNhaCungCap.getText()));
            }
        }

        private ArrayList<CTPhieuNhapDTO> getCTPhieuNhap(LocalDate date1, LocalDate date2, String SP, String NV, String KH) {
            ArrayList<CTPhieuNhapDTO> result = new ArrayList<>();
            for (CTPhieuNhapDTO ctpn : qlCTPN.list) {
                PhieuNhapDTO pn = qlPN.getPN(ctpn.getMaPhieuNhap());
                NhanVienDTO nv = qlNV.getNV(pn.getMaNhanVien());
                NhaCungCapDTO kh = qlNCC.getNCC(pn.getMaNCC());
                SanPhamDTO sp = qlSP.getSanPham(ctpn.getMaSanPham());
                if ((Tool.removeAccent(sp.getTenSanPham()).contains(Tool.removeAccent(SP)) && !SP.equals(""))
                        || (Tool.removeAccent(sp.getMaSanPham()).contains((Tool.removeAccent(SP))) && !SP.equals(""))
                        || (Tool.removeAccent(kh.getTenNCC()).contains((Tool.removeAccent(KH))) && !KH.equals(""))
                        || (Tool.removeAccent(kh.getMaNCC()).contains((Tool.removeAccent(KH))) && !KH.equals(""))
                        || (Tool.removeAccent(nv.getFullFame()).contains(Tool.removeAccent(NV)) && !NV.equals(""))
                        || (Tool.removeAccent(nv.getMaNhanVien()).contains(Tool.removeAccent(NV)) && !NV.equals(""))) {
                    result.add(ctpn);
                }
                if (date1 != null) {
                    if (pn.getNgayLap().before(Date.valueOf(date1))) {
                        result.remove(ctpn);
                    }
                }
                if (date2 != null) {
                    if (pn.getNgayLap().after(Date.valueOf(date2))) {
                        result.remove(ctpn);
                    }
                }
            }
            return result;
        }

        private ArrayList<CTHoaDonDTO> getCTHOADON(LocalDate date1, LocalDate date2, String SP, String NV, String KH) {
            ArrayList<CTHoaDonDTO> result = new ArrayList<>();
            for (CTHoaDonDTO ct : qlCTHD.list) {
                HoaDonDTO hd = qlHD.getHD(ct.getMaHoaDon());
                NhanVienDTO nv = qlNV.getNV(hd.getMaNhanVien());
                KhachHangDTO kh = qlKH.getKH(hd.getMaKhachHang());
                SanPhamDTO sp = qlSP.getSanPham(ct.getMaSanPham());
                boolean check = true;

                if (date1 != null) {
                    if (!(hd.getNgayLap().after(Date.valueOf(date1)))) {
                        check = false;
                    }
                }
                if (date2 != null) {
                    if (!(hd.getNgayLap().before(Date.valueOf(date2)))) {
                        check = false;
                    }
                }
                if (!KH.equals("")) {
                    if (!hd.getMaKhachHang().contains(KH) && !Tool.removeAccent(kh.getFullName()).contains(Tool.removeAccent(KH))) {
                        System.out.println(KH);
                        check = false;
                    }
                }
                if (!NV.equals("")) {
                    if (!hd.getMaNhanVien().contains(NV) && !NV.equals("") && !Tool.removeAccent(nv.getFullFame()).contains(Tool.removeAccent(NV)) && !NV.equals("")) {
                        check = false;
                    }
                }
                if (!SP.equals("")) {
                    if (!Tool.removeAccent(ct.getMaSanPham()).toLowerCase().contains(Tool.removeAccent(SP).toLowerCase()) && !Tool.removeAccent(sp.getTenSanPham()).toLowerCase().contains(Tool.removeAccent(SP).toLowerCase())) {
                        check = false;
                    }
                }

                if (check) {
                    result.add(ct);
                }

            }
            return result;
        }

        private class workspace extends JPanel {

            JTabbedPane menu = new JTabbedPane(JTabbedPane.TOP);
            JPanel Tong = new JPanel(new FlowLayout());
            HoaDon banra = new HoaDon();
            NhapHang nhaphang = new NhapHang();

            public workspace() {
                menu.setPreferredSize(new Dimension(1920 - 300, 750));
                add(menu);
                //Tong.setBackground(Color.darkGray);
                Tong.add(TongSo("S???n ph???m", qlSP.list.size()));
                stTong += "\n"
                        + "S???n ph???m : " + qlSP.list.size();
                Tong.add(TongSo("Nh??n vi??n", qlNV.list.size()));
                stTong += "\n"
                        + "Nh??n vi??n : " + qlNV.list.size();
                Tong.add(TongSo("Kh??ch h??ng", qlKH.list.size()));
                stTong += "\n"
                        + "Kh??ch h??ng : " + qlKH.list.size();
                Tong.add(TongSo("Nh?? cung c???p", qlNCC.list.size()));
                stTong += "\n"
                        + "Nh?? cung c???p : " + qlNCC.list.size();
                Date now = Tool.getDate(LocalDate.now().toString());
                int month = now.getMonth() + 1;
                Tong.add(TongSo("Th??ng thu", ThongKeDAO.getThuNhapThang(month + "", month + 1 + "", now.getYear() + 1900 + "")));
                stTong += "\n"
                        + "Th??ng thu : " + ThongKeDAO.getThuNhapThang(month + "", month + 1 + "", now.getYear() + 1900 + "");
                Tong.add(TongSo("Th??ng chi", ThongKeDAO.getChiThang(month + "", month + 1 + "", now.getYear() + 1900 + "")));
                stTong += "\n"
                        + "Th??ng chi : " + ThongKeDAO.getChiThang(month + "", month + 1 + "", now.getYear() + 1900 + "");
                Tong.add(TongSo("N??m thu", ThongKeDAO.getThuNhapNam(now.getYear() + 1900 + "")));
                stTong += "\n"
                        + "N??m thu: " + ThongKeDAO.getThuNhapNam(now.getYear() + 1900 + "");
                Tong.add(TongSo("N??m chi", ThongKeDAO.getChiNam((now.getYear() + 1900 + ""))));
                stTong += "\n"
                        + "N??m chi : " + ThongKeDAO.getChiNam((now.getYear() + 1900 + ""));
                Tong.setPreferredSize(new Dimension(1620, 700));

                menu.addTab("T???ng", new ImageIcon("src/Image/bar_chart_50px.png"), Tong);
                menu.setSelectedComponent(Tong);
                //BanRa
                banra.setPreferredSize(new Dimension(1620, 700));
                menu.addTab("B??n ra", new ImageIcon("src/Image/banhang.png"), banra);
                menu.addTab("Nh???p h??ng", new ImageIcon("src/Image/banhang.png"), nhaphang);
                menu.addChangeListener((e) -> {
                    if (menu.getSelectedComponent().equals(banra)) {
                        pltimKiemNhaCungCap.setBorder(BorderFactory.createTitledBorder("Kh??ch h??ng"));
                    }
                    if (menu.getSelectedComponent().equals(nhaphang)) {
                        pltimKiemNhaCungCap.setBorder(BorderFactory.createTitledBorder("Nh?? cung c???p"));
                    }
                });

            }

        }

        public class NhapHang extends JPanel {

            FormHienThi tb = new FormHienThi();
            JPanel TongKet = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel lbHD = new JLabel();
            JLabel lbSP = new JLabel();
            JLabel lbKH = new JLabel();
            JLabel lbNV = new JLabel();
            JLabel lbBanra = new JLabel();

            public NhapHang() {
                setLayout(new BorderLayout());
                String[] header = new String[]{
                    "STT",
                    "Phi???u nh???p",
                    "T??n nh??n vi??n",
                    "T??n nh?? cung c???p",
                    "T??n s???n ph???m",
                    "S??? l?????ng",
                    "????n gi??",
                    "Th??nh ti???n"
                };
                stNhap=String.format("%-5s%-8s%-25s%-25s%-30s%-10s%-10s%-15s", "STT",
                    "Phi???u nh???p",
                    "T??n nh??n vi??n",
                    "T??n nh?? cung c???p",
                    "T??n s???n ph???m",
                    "S??? l?????ng",
                    "????n gi??",
                    "Th??nh ti???n");
                tb.setHeaders(header);
                setDatatoTable(qlCTPN.list);
                add(tb, BorderLayout.CENTER);
                TongKet.add(lbHD);
                TongKet.add(lbSP);
                TongKet.add(lbKH);
                TongKet.add(lbNV);
                TongKet.add(lbBanra);
                lbHD.setSize(200, 100);
                lbSP.setSize(200, 100);
                lbKH.setSize(200, 100);
                lbNV.setSize(200, 100);
                lbBanra.setSize(300, 100);
                lbHD.setPreferredSize(new Dimension(200, 100));;
                lbSP.setPreferredSize(new Dimension(200, 100));;
                lbKH.setPreferredSize(new Dimension(200, 100));;
                lbNV.setPreferredSize(new Dimension(200, 100));;
                lbBanra.setPreferredSize(new Dimension(300, 100));
                lbHD.setBorder(BorderFactory.createEtchedBorder(1));
                lbSP.setBorder(BorderFactory.createEtchedBorder(1));
                lbKH.setBorder(BorderFactory.createEtchedBorder(1));
                lbNV.setBorder(BorderFactory.createEtchedBorder(1));
                lbBanra.setBorder(BorderFactory.createEtchedBorder(1));
                add(TongKet, BorderLayout.SOUTH);
                tb.getTable().setupSort();
            }

            public void setDatatoTable(ArrayList<CTPhieuNhapDTO> list) {
                tb.getTable().clear();
                int stt = 1;
                String containKhach = "";
                String containNhanVien = "";
                String containSanPham = "";
                String containHoaDon = "";
                int demkhach = 0;
                int demnhanvien = 0;
                int demsanpham = 0;
                int demhoadon = 0;
                int Tong = 0;
                for (CTPhieuNhapDTO ct : list) {
                    PhieuNhapDTO pn = qlPN.getPN(ct.getMaPhieuNhap());
                    NhanVienDTO nv = qlNV.getNV(pn.getMaNhanVien());
                    NhaCungCapDTO kh = qlNCC.getNCC(pn.getMaNCC());
                    SanPhamDTO sp = qlSP.getSanPham(ct.getMaSanPham());
                    if (!containKhach.contains(kh.getMaNCC())) {
                        containKhach = containKhach + kh.getMaNCC();
                        demkhach++;
                    }
                    if (!containNhanVien.contains(nv.getMaNhanVien())) {
                        containNhanVien += nv.getMaNhanVien();
                        demnhanvien++;
                    }
                    if (!containSanPham.contains(sp.getMaSanPham())) {
                        containSanPham += sp.getMaSanPham();
                        demsanpham++;
                    }
                    if (!containHoaDon.contains(pn.getMaPhieuNhap())) {
                        containHoaDon += pn.getMaPhieuNhap();
                        demhoadon++;
                    }
                    Tong += ct.getDongia() * ct.getSoLuong();
                    tb.getTable().addRow(new String[]{
                        stt + "",
                        ct.getMaPhieuNhap(),
                        nv.getFullFame(),
                        kh.getTenNCC(),
                        sp.getTenSanPham(),
                        ct.getSoLuong() + "",
                        ct.getDongia() + "",
                        ct.getDongia() * ct.getSoLuong() + ""});
                    stNhap+="\n"+String.format("%-5s%-8s%-25s%-25s%-30s%-10s%-10s%-15s",stt + "",
                        ct.getMaPhieuNhap(),
                        nv.getFullFame(),
                        kh.getTenNCC(),
                        sp.getTenSanPham(),
                        ct.getSoLuong() + "",
                        ct.getDongia() + "",
                        ct.getDongia() * ct.getSoLuong() + "");
                    stt++;
                    
                }
                lbHD.setText(demhoadon + " Phi???u nh???p");
                lbSP.setText(demsanpham + " S???n ph???m");
                lbKH.setText(demkhach + " Nh?? cung c???p");
                lbNV.setText(demnhanvien + " Nh??n vi??n");
                lbBanra.setText("T???ng: " + Tool.getMonney(Tong) + ",000 vn??");

            }
        }

        public class HoaDon extends JPanel {

            MyTable tb = new MyTable();
            JPanel TongKet = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel lbHD = new JLabel();
            JLabel lbSP = new JLabel();
            JLabel lbKH = new JLabel();
            JLabel lbNV = new JLabel();
            JLabel lbBanra = new JLabel();

            public HoaDon() {
                setLayout(new BorderLayout());
                String[] header = new String[]{
                    "STT",
                    "H??a ????n",
                    "T??n nh??n vi??n",
                    "T??n kh??ch h??ng",
                    "T??n s???n ph???m",
                    "S??? L?????ng",
                    "????n gi??",
                    "Th??nh ti???n"
                };
                stBan=String.format("%-5s%-10s%-30s%-10s%-30s$-40s","STT",
                    "H??a ????n",
                    "T??n s???n ph???m",
                    "S??? L?????ng",
                    "????n gi??",
                    "Th??nh ti???n");
                tb.setHeaders(header);
                setDatatoTable(qlCTHD.list);
                add(tb, BorderLayout.CENTER);
                TongKet.add(lbHD);
                TongKet.add(lbSP);
                TongKet.add(lbKH);
                TongKet.add(lbNV);
                TongKet.add(lbBanra);
                lbHD.setSize(200, 100);
                lbSP.setSize(200, 100);
                lbKH.setSize(200, 100);
                lbNV.setSize(200, 100);
                lbBanra.setSize(300, 100);
                lbHD.setPreferredSize(new Dimension(200, 100));;
                lbSP.setPreferredSize(new Dimension(200, 100));;
                lbKH.setPreferredSize(new Dimension(200, 100));;
                lbNV.setPreferredSize(new Dimension(200, 100));;
                lbBanra.setPreferredSize(new Dimension(300, 100));
                lbHD.setBorder(BorderFactory.createEtchedBorder(1));
                lbSP.setBorder(BorderFactory.createEtchedBorder(1));
                lbKH.setBorder(BorderFactory.createEtchedBorder(1));
                lbNV.setBorder(BorderFactory.createEtchedBorder(1));
                lbBanra.setBorder(BorderFactory.createEtchedBorder(1));
                add(TongKet, BorderLayout.SOUTH);
                tb.setupSort();
            }

            public void setDatatoTable(ArrayList<CTHoaDonDTO> list) {
                tb.clear();
                int stt = 0;
                String containKhach = "";
                String containNhanVien = "";
                String containSanPham = "";
                String containHoaDon = "";
                int demkhach = 0;
                int demnhanvien = 0;
                int demsanpham = 0;
                int demhoadon = 0;
                int Tong = 0;
                for (CTHoaDonDTO ct : list) {
                    HoaDonDTO hd = qlHD.getHD(ct.getMaHoaDon());
                    NhanVienDTO nv = qlNV.getNV(hd.getMaNhanVien());
                    KhachHangDTO kh = qlKH.getKH(hd.getMaKhachHang());
                    SanPhamDTO sp = qlSP.getSanPham(ct.getMaSanPham());
                    if (!containKhach.contains(kh.getMaKhachHang())) {
                        containKhach = containKhach + kh.getMaKhachHang();
                        demkhach++;
                    }
                    if (!containNhanVien.contains(nv.getMaNhanVien())) {
                        containNhanVien += nv.getMaNhanVien();
                        demnhanvien++;
                    }
                    if (!containSanPham.contains(sp.getMaSanPham())) {
                        containSanPham += sp.getMaSanPham();
                        demsanpham++;
                    }
                    if (!containHoaDon.contains(hd.getMaHoaDon())) {
                        containHoaDon += hd.getMaHoaDon();
                        demhoadon++;
                    }
                    Tong += ct.getThanhTien();
                    tb.addRow(new String[]{
                        stt + "",
                        ct.getMaHoaDon(),
                        nv.getFullFame(),
                        kh.getFullName(),
                        sp.getTenSanPham(),
                        ct.getSoLuong() + "",
                        ct.getDonGia() + "",
                        ct.getThanhTien() + "",});
                stBan+="\n"+String.format("%-5s%-10s%-30s%-10s%-30s$-40s",stt,
                    ct.getMaHoaDon(),
                    sp.getTenSanPham(),
                    ct.getSoLuong() + "",
                    ct.getDonGia() + "",
                    ct.getThanhTien() + "");
                stt++;
                }

                lbHD.setText(demhoadon + " H??a ????n");
                lbSP.setText(demsanpham + " S???n ph???m");
                lbKH.setText(demkhach + " Kh??ch h??ng");
                lbNV.setText(demnhanvien + " Nh??n vi??n");
                lbBanra.setText("T???ng: " + Tool.getMonney(Tong) + ",000 vn??");

            }
        }

        public JPanel TongSo(String type, int sl) {
            JPanel panel = new JPanel(null);
            Font font = new Font("Segui", 0, 25);
            Font font1 = new Font("Segui", 0, 30);
            panel.setSize(350, 300);
            panel.setPreferredSize(new Dimension(350, 300));
            //panel.setBackground(Color.DARK_GRAY);
            JLabel image = new JLabel();
            JLabel title = new JLabel();
            JLabel lbsl = new JLabel();
            image.setBounds(30, 30, 100, 150);
            title.setBounds(150, 50, 150, 50);
            title.setFont(font);
            lbsl.setBounds(200, 150, 100, 50);
            lbsl.setFont(font1);
            if (type.equals("S???n ph???m")) {
                Tool.setPicture(image, "product_100px.png");
                title.setText("S???n ph???m");
                lbsl.setText(sl + "");
            } else if (type.equals("Nh??n vi??n")) {
                Tool.setPicture(image, "online_support_100px.png");
                title.setText("Nh??n Vi??n");
                lbsl.setText(sl + "");
            } else if (type.equals("Kh??ch h??ng")) {
                Tool.setPicture(image, "account_100px.png");
                title.setText("Kh??ch h??ng");
                lbsl.setText(sl + "");
            } else if (type.equals("Nh?? cung c???p")) {
                Tool.setPicture(image, "company_50px.png");
                title.setText("Nh?? cung c???p");
                lbsl.setText(sl + "");
            } else {
                Tool.setPicture(image, "money_100px.png");
                title.setText(type);
                lbsl.setText(Tool.getMonney(sl) + ",000 vn??");
                lbsl.setBounds(10, 200, 300, 40);
            }
            panel.add(image);
            panel.add(lbsl);
            panel.add(title);
            panel.setBorder(BorderFactory.createBevelBorder(1));
            return panel;
        }
    }
}
