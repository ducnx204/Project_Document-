<?php
$sql_loai = "SELECT * FROM `category` ORDER BY `category`.`id` ASC";
$rowCategory = mysqli_query($conn, $sql_loai);
?>
<div class="box_list">
    <div class="tieude">
        <h3>Loại sản phẩm</h3>
    </div>
    <ul class="list">
        <?php
        while ($category_id = mysqli_fetch_assoc($rowCategory)) {
        ?>
        <li><a
                href="index.php?quanly=category&id=<?php echo $category_id['id'] ?>"><?php echo $category_id['name_category'] ?></a>
        </li>
        <?php
        }
        ?>
    </ul>
</div>

<div class="box_list">

    <div class="tieude">
        <h3>Hàng bán chạy</h3>
    </div>
    <?php
    $sqlProduct = "SELECT * FROM `product` ORDER BY `product`.`id` DESC LIMIT 4 ";
    $rowProduct = mysqli_query($conn, $sqlProduct);
    ?>
    <ul class="hangbanchay">
        <?php
        while ($Product = mysqli_fetch_assoc($rowProduct)) {
        ?>
        <li><a href="?quanly=chitietsp&idloaisp=<?php echo $Product['id'] ?>&id=<?php echo $Product['id'] ?>">
                <img src="admincp/modules/quanlysanpham/images/<?php echo $Product['imageview_product'] ?>" width="150"
                    height="150" />
                <p><?php echo $Product['name_product'] ?></p>
                <p style="color:red;"><?php echo number_format($Product['price_product']) . ' ' . 'đ' ?></p>
            </a></li>
        <?php
        }
        ?>
    </ul>
</div>