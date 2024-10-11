@extends('layouts.admin')
 
@section('title')
<title>Trang chu</title>
 @endsection
@section('content')
<div class="content-wrapper" >
  <!-- Content Header (Page header) -->
  @include('partials.content-header', ['name' => 'category' , 'key' => 'Add'])

    <div class="container-fluid">
      <div class="row">
        <div class="col-md-6">
            <form action="{{ route('categories.store') }}" method="POST">
              @csrf
                <div class="form-group">
                  <label >Tên danh mục</label>
                  <input type="text" name="name" class="form-control"  placeholder="Nhập tên danh mục">
                </div>
                <div class="form-group">
                    <label >Chọn danh mục cha</label>
                    <select class="form-control" name="parent_id">
                      <option value="0" >-- Danh mục cha --</option>
                      {!! $htmlSelect !!}
                    </select>
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

