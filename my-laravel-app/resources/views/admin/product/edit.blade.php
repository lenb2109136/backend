@extends('layouts.admin')

@section('title')
    <title>Trang chu</title>
@endsection
@section('css')
    <link href="{{ asset('vendors/select2/select2.min.css') }}" rel="stylesheet" />
    <link href="{{ asset('admins/product/add/add.css') }}" rel="stylesheet" />
@endsection
@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        @include('partials.content-header', ['name' => 'category', 'key' => 'Edit'])

        <form action="{{ route('product.update', ['id' => $product->id]) }}" method="POST" enctype="multipart/form-data">
            <div class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            @csrf
                            <div class="form-group">
                                <label>Tên sản phẩm</label>
                                <input type="text" name="name" class="form-control" placeholder="Nhập tên danh mục"
                                    value="{{ $product->name }}">
                            </div>

                            <div class="form-group">
                                <label>Giá sản phẩm</label>
                                <input type="text" name="price" class="form-control" placeholder="Giá sản phẩm"
                                    value="{{ $product->price }}">
                            </div>

                            <div class="form-group">
                                <label>Ảnh đại diện</label>
                                <input type="file" name="feature_image_path" class="form-control-file mb-3"
                                    style="border: 2px dashed #ccc; border-radius: 5px; padding: 10px;">

                                <div class="text-left">
                                    <img src="{{ $product->feature_image_path }}" alt=""
                                        style="width: 250px; height: 200px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
                                </div>
                            </div>


                            <div class="container">
                                <div class="form-group">
                                    <label>Ảnh chi tiết</label>
                                    <input type="file" multiple name="image_path[]" class="form-control-file mb-3"
                                        style="border: 2px dashed #ccc; border-radius: 5px; padding: 10px;">

                                    <div class="row">
                                        @foreach ($product->productImages as $productImageItem)
                                            <div class="col-md-3 mb-3">
                                                <img src="{{ $productImageItem->image_path }}" alt=""
                                                    style="width: 100%; height: auto; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
                                            </div>
                                        @endforeach
                                    </div>
                                </div>
                            </div>


                            <div class="form-group">
                                <label>Chọn danh mục cha</label>
                                <select class="form-control select2_init" name="category_id">
                                    <option value="">-- Danh mục cha --</option>
                                    {!! $htmlSelect !!}
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Nhập tag cho sản phẩm</label>
                                <select name="tags[]" class="form-control tag_select_choose" multiple="multiple">
                                    @foreach ($product->tags as $tagItem)
                                        <option value="{{ $tagItem->name }}" selected> {{ $tagItem->name }}</option>
                                    @endforeach
                                </select>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Nhập nội dung</label>
                                <textarea class="form-control tinymce_editor_init" name="contents" rows="3">{{ $product->content }}</textarea>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>

                        <!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
        </form>
    </div>
@endsection

@section('js')
    <script src="{{ asset('vendors/select2/select2.min.js') }}"></script>
    <script src="https://cdn.tiny.cloud/1/n31n08ekms31210zb5xr6385bqnsz15k1h9n2ucic1mpprdh/tinymce/5/tinymce.min.js"
        referrerpolicy="origin"></script>
    <script src="{{ asset('admins/product/add/add.js') }}"></script>
@endsection
