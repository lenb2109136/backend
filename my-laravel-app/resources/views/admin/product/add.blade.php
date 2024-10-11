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
        @include('partials.content-header', ['name' => 'category', 'key' => 'Add'])
        {{-- <div class="col-md-12">
            @if ($errors->any())
                <div class="alert alert-danger">
                    <ul>
                        @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                        @endforeach
                    </ul>
                </div>
            @endif
        </div> --}}
        <form action="{{ route('product.store') }}" method="POST" enctype="multipart/form-data">
            <div class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            @csrf
                            <div class="form-group">
                                <label class="@error('name') is-invalid @enderror">Tên sản phẩm</label>
                                <input type="text" name="name"
                                    class="form-control @error('name') is-invalid @enderror" placeholder="Nhập tên sản phẩm"
                                    value="{{ old('name') }}">
                                @error('name')
                                    <div class="alert alert-fix alert-danger">{{ $message }}</div>
                                @enderror
                            </div>


                            <div class="form-group">
                                <label class="@error('price') is-invalid @enderror">Giá sản phẩm</label>
                                <input type="text" name="price"
                                    class="form-control @error('price') is-invalid @enderror" placeholder="Giá sản phẩm"
                                    value="{{ old('price') }}">
                                @error('price')
                                    <div class="alert alert-fix alert-danger">{{ $message }}</div>
                                @enderror
                            </div>


                            <div class="form-group">
                                <label>Ảnh đại diện</label>
                                <input type="file" name="feature_image_path" class="form-control-file mb-3 "
                                    style="border: 2px dashed #ccc; border-radius: 5px; padding: 10px;">
                            </div>

                            <div class="form-group">
                                <label>Ảnh chi tiết</label>
                                <input type="file" multiple name="image_path[]" class="form-control-file mb-3"
                                    style="border: 2px dashed #ccc; border-radius: 5px; padding: 10px;">
                            </div>

                            <div class="form-group">
                                <label class="@error('category_id') is-invalid @enderror">Chọn danh mục cha</label>
                                <select class="form-control select2_init  @error('category_id') is-invalid @enderror"
                                    name="category_id">
                                    <option value="">-- Danh mục cha --</option>
                                    {!! $htmlSelect !!}
                                </select>
                                @error('category_id')
                                    <div class="alert alert-fix alert-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <div class="form-group">
                                <label>Nhập tag cho sản phẩm</label>
                                <select name="tags[]" class="form-control tag_select_choose" multiple="multiple">
                                    <!-- Tags will be populated here -->
                                </select>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="@error('contents') is-invalid @enderror">Nhập nội dung</label>
                                <textarea class="form-control tinymce_editor_init @error('contents') is-invalid @enderror" name="contents"
                                    rows="3">{{ old('contents') }}</textarea>
                                @error('contents')
                                    <div class="alert alert-fix alert-danger">{{ $message }}</div>
                                @enderror
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
