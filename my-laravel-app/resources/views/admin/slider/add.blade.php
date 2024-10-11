@extends('layouts.admin')

@section('title')
    <title>Trang chu</title>
@endsection
@section('css')
    <link href="{{ asset('admins/slider/add/add.css') }}" rel="stylesheet" />
@endsection
@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        @include('partials.content-header', ['name' => 'Slider', 'key' => 'Add'])

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <form action="{{ route('slider.store') }}" method="POST" enctype="multipart/form-data">
                        @csrf
                        <div class="form-group">
                            <label class="@error('name') is-invalid @enderror">Tên Slider</label>
                            <input type="text" name="name" class="form-control @error('name') is-invalid @enderror"
                                value="{{ old('name') }}" placeholder="Nhập tên slider">
                            @error('name')
                                <div class="alert alert-fix alert-danger">{{ $message }}</div>
                            @enderror
                        </div>

                        <div class="form-group">
                            <label class="@error('description') is-invalid @enderror">Mô tả</label>
                            <textarea name="description" rows="4" class="form-control @error('description') is-invalid @enderror"
                                placeholder="Nhập mô tả">{{ old('description') }}</textarea>
                            @error('description')
                                <div class="alert alert-fix alert-danger">{{ $message }}</div>
                            @enderror
                        </div>


                        <div class="form-group">
                            <label class="@error('image_path') is-invalid @enderror">Hình ảnh</label>
                            <input type="file" name="image_path"
                                class="form-control-file mb-3 @error('image_path') is-invalid @enderror"
                                style="border: 2px dashed #ccc; border-radius: 5px; padding: 10px;">
                            @error('image_path')
                                <div class="alert alert-fix alert-danger alert-fix-file ">{{ $message }}</div>
                            @enderror
                        </div>


                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>

            </div>

            <!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>

    </div>
@endsection
