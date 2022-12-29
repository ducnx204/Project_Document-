	<?php
	$sql_moinhat = "SELECT * FROM `product` ORDER BY `product`.`id` DESC limit 0,6";
	$row_moinhat = mysqli_query($conn, $sql_moinhat);
	?>
	<div class="tieude">Sản phẩm mới nhất</div>
	<ul class="product">
	    <?php
		while ($dong_moinhat = mysqli_fetch_assoc($row_moinhat)) {
		?>
	    <li><a
	            href="?quanly=chitietsp&id_category=<?php echo $dong_moinhat['id_category'] ?>&id=<?php echo $dong_moinhat['id'] ?>">
	            <img src="admincp/modules/quanlysanpham/images/<?php echo $dong_moinhat['imageview_product'] ?>"
	                width="150" height="150" />
	            <p style="color:skyblue"><?php echo $dong_moinhat['name_product'] ?></p>
	            <p style="color:red;font-weight:bold; border:1px solid #d9d9d9; width:150px;
                            height:30px; line-height:30px;margin-left:35px;margin-bottom:5px;">
	                <?php echo number_format($dong_moinhat['price_product']) . ' ' . 'đ' ?></p>
	        </a></li>
	    <?php
		}
		?>
	</ul>
	<div class="clear"></div>

	<?php

	$sql_loai = "SELECT * FROM `category`";
	$row_loai = mysqli_query($conn, $sql_loai);
	while ($dong_loai = mysqli_fetch_assoc($row_loai)) {
		echo '<div class="tieude">' . $dong_loai['name_category'] . '</div>';
		$sql_loaisp = "SELECT * from category inner join product on product.id_category=category.id where product.id_category='" . $dong_loai['id'] . "'";
		$row = mysqli_query($conn, $sql_loaisp);
		$count = mysqli_num_rows($row);
		if ($count > 0) {
	?>

	<ul class="product">
	    <?php
				while ($dong = mysqli_fetch_array($row)) {
				?>
	    <li><a href="?quanly=chitietsp&id_category=<?php echo $dong['id_category'] ?>&id=<?php echo $dong['id'] ?>">
	            <img src="<?php echo $dong['imageview_product'] ?>" width="150" height="150" />

	            <p style="color:skyblue"><?php echo $dong['name_product'] ?></p>
	            <p style="color:red;font-weight:bold; border:1px solid #d9d9d9; width:150px;
                            height:30px; line-height:30px;margin-left:35px;margin-bottom:5px;">
	                <?php echo number_format($dong['price_product']) . ' ' . 'đ' ?></p>


	        </a></li>
	    <?php
				}
			} else {
				echo '<h3 style="margin:5px;font-style:italic;color:#000">Hiện chưa có sản phẩm...</h3>';
			}


			?>
	</ul>
	<div class="clear"></div>
	<?php


	}


		?>